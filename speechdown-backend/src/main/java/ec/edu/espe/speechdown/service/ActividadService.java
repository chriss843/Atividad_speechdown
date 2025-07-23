package ec.edu.espe.speechdown.service;

import ec.edu.espe.speechdown.dto.ActividadRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class ActividadService {

    private final RestTemplate restTemplate;
    private final String apiUrl;

    public ActividadService(RestTemplate restTemplate,
                            @Value("${gemini.api.key}") String apiKey,
                            @Value("${gemini.api.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl + "?key=" + apiKey;
    }

    @SuppressWarnings("unchecked")
    public String generarActividad(ActividadRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> requestBody = new HashMap<>();
            Map<String, Object> content = new HashMap<>();
            Map<String, String> part = new HashMap<>();

            part.put("text", construirPromptTerapeutico(request));
            content.put("parts", List.of(part));
            requestBody.put("contents", List.of(content));

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            try {
                ResponseEntity<Map> response = restTemplate.postForEntity(
                        apiUrl,
                        entity,
                        Map.class
                );

                return procesarRespuestaGemini(response);
            } catch (HttpServerErrorException.ServiceUnavailable e) {
                // Manejo específico para error 503
                return "El servicio de generación está temporalmente sobrecargado. Por favor intente nuevamente en unos minutos.";
            } catch (RestClientException e) {
                // Manejo de otros errores de conexión
                return "Error al conectar con el servicio de generación: " + e.getMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Ocurrió un error inesperado al generar la actividad.";
        }
    }
    private String construirPromptTerapeutico(ActividadRequest request) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Genera una actividad terapéutica para ");
        prompt.append(request.getNombre()).append(", de ").append(request.getEdad()).append(" años. ");
        prompt.append("La actividad debe ser de tipo: ").append(String.join(", ", request.getCategorias())).append(". ");

        // Personalización según categorías
        if (request.getCategorias().contains("juego")) {
            prompt.append("Diseña un juego interactivo que ");
        }
        if (request.getCategorias().contains("cancion")) {
            prompt.append("Incluye una canción o rima simple que ");
        }
        if (request.getCategorias().contains("reto")) {
            prompt.append("Propón un reto alcanzable que ");
        }

        // Objetivos según edad
        prompt.append("ayude a desarrollar ");
        if (request.getEdad() <= 5) {
            prompt.append("habilidades fonológicas básicas ");
        } else if (request.getEdad() <= 8) {
            prompt.append("la articulación de sonidos ");
        } else {
            prompt.append("el lenguaje expresivo ");
        }

        prompt.append("usando palabras con sílabas directas. Máximo 10 instrucciones claras.");

        return prompt.toString();
    }

    @SuppressWarnings("unchecked")
    private String procesarRespuestaGemini(ResponseEntity<Map> response) {
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            Map<String, Object> body = response.getBody();
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) body.get("candidates");
            if (candidates != null && !candidates.isEmpty()) {
                Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
                List<Map<String, String>> parts = (List<Map<String, String>>) content.get("parts");
                if (parts != null && !parts.isEmpty()) {
                    return parts.get(0).get("text");
                }
            }
        }
        return "No se pudo generar la actividad en este momento.";
    }
}