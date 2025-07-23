package ec.edu.espe.speechdown.controller;

import ec.edu.espe.speechdown.service.TextToSpeechService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/speech")
public class TtsTestController {

    private final TextToSpeechService ttsService;

    public TtsTestController(TextToSpeechService ttsService) {
        this.ttsService = ttsService;
    }

    @PostMapping("/synthesize")
    public ResponseEntity<byte[]> synthesizeSpeech(@RequestBody String text) {
        try {
            byte[] audioContent = ttsService.synthesizeSpeech(text);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("audio/mpeg"))
                    .header("Content-Disposition", "inline; filename=speech.mp3")
                    .body(audioContent);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(("Error al generar audio: " + e.getMessage()).getBytes());
        }
    }
}