package ec.edu.espe.speechdown.service;

import ec.edu.espe.speechdown.dto.CuentoRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CuentoService {

    private final RestTemplate restTemplate;
    private final String apiUrl;

    public CuentoService(RestTemplate restTemplate,
                         @Value("${gemini.api.key}") String apiKey,
                         @Value("${gemini.api.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl + "?key=" + apiKey;
    }

    @SuppressWarnings("unchecked")
    public String generarCuento(CuentoRequest request) {
        try {
            // 1. Usar los headers correctos de Spring
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

            // 2. Preparar el cuerpo de la petición
            Map<String, Object> requestBody = new HashMap<>();
            Map<String, Object> content = new HashMap<>();
            Map<String, String> part = new HashMap<>();

            part.put("text", construirPrompt(request));
            content.put("parts", List.of(part));
            requestBody.put("contents", List.of(content));

            // 3. Crear HttpEntity con los tipos correctos
            org.springframework.http.HttpEntity<Map<String, Object>> entity =
                    new org.springframework.http.HttpEntity<>(requestBody, headers);

            // 4. Enviar la petición
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    apiUrl,
                    entity,
                    Map.class
            );

            // 5. Procesar la respuesta
            return procesarRespuesta(response);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al generar el cuento: " + e.getMessage();
        }
    }

    private String construirPrompt(CuentoRequest request) {
        return String.format(
                "Genera un cuento infantil de máximo 50 palabras para %s sobre %s. " +
                        "Debe ser simple, con moraleja positiva y lenguaje adecuado para niños.",
                request.getNombre(),
                String.join(", ", request.getCategorias())
        );
    }

    @SuppressWarnings("unchecked")
    private String procesarRespuesta(ResponseEntity<Map> response) {
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            Map<String, Object> body = response.getBody();
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) body.get("candidates");
            if (candidates != null && !candidates.isEmpty()) {
                Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
                List<Map<String, String>> parts = (List<Map<String, String>>) content.get("parts");
                if (parts != null && !parts.isEmpty()) {
                    return limitarPalabras(parts.get(0).get("text"), 40);
                }
            }
        }
        return "No se pudo generar el cuento en este momento.";
    }

    private String limitarPalabras(String texto, int maxPalabras) {
        if (texto == null) return "";
        String[] palabras = texto.split("\\s+");
        return palabras.length <= maxPalabras
                ? texto
                : String.join(" ", Arrays.copyOfRange(palabras, 0, maxPalabras)) + "...";
    }
}