package ec.edu.espe.speechdown.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders; // Este es el correcto

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class TextToSpeechService {
    private static final String TTS_API_URL = "https://texttospeech.googleapis.com/v1/text:synthesize";
    private final RestTemplate restTemplate;
    private final String apiKey;

    public TextToSpeechService(
            RestTemplate restTemplate,
            @Value("${google.cloud.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    public byte[] synthesizeSpeech(String text) {
        try {
            // 1. Construir el cuerpo de la solicitud
            Map<String, Object> requestBody = new HashMap<>();

            // Configuración de entrada
            Map<String, String> input = new HashMap<>();
            input.put("text", text);
            requestBody.put("input", input);

            // Configuración de voz
            Map<String, String> voice = new HashMap<>();
            voice.put("languageCode", "es-ES");
            voice.put("name", "es-ES-Standard-A");
            requestBody.put("voice", voice);

            // Configuración de audio
            Map<String, String> audioConfig = new HashMap<>();
            audioConfig.put("audioEncoding", "MP3");
            requestBody.put("audioConfig", audioConfig);

            // 2. Configurar headers (usando org.springframework.http.HttpHeaders)
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-Goog-Api-Key", apiKey); // Mejor práctica que poner el key en la URL

            // 3. Hacer la petición (con tipo explícito para HttpEntity)
            ResponseEntity<Map> response = restTemplate.exchange(
                    TTS_API_URL,
                    HttpMethod.POST,
                    new HttpEntity<Map<String, Object>>(requestBody, headers),
                    Map.class
            );

            // 4. Procesar la respuesta
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                String audioContent = (String) response.getBody().get("audioContent");
                if (audioContent == null) {
                    throw new RuntimeException("La respuesta no contiene audioContent");
                }
                return Base64.getDecoder().decode(audioContent);
            } else {
                throw new RuntimeException("Error en la respuesta de la API: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al sintetizar voz: " + e.getMessage(), e);
        }
    }
}