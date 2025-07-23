package ec.edu.espe.speechdown.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "google.cloud")
public class GoogleCloudConfig {
    private String apiKey;
    private TtsConfig tts;

    // Getters y Setters

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public TtsConfig getTts() {
        return tts;
    }

    public void setTts(TtsConfig tts) {
        this.tts = tts;
    }

    public static class TtsConfig {
        private String projectId;
        private String audioEncoding;

        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }

        public String getAudioEncoding() {
            return audioEncoding;
        }

        public void setAudioEncoding(String audioEncoding) {
            this.audioEncoding = audioEncoding;
        }

    }
}