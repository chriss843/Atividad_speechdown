package ec.edu.espe.speechdown.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguracion implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Aplica la configuración CORS a todas las rutas bajo /api/
                .allowedOrigins("http://localhost:3000") // Permite solicitudes solo desde tu frontend de React
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos HTTP permitidos
                .allowedHeaders("*") // Permite todas las cabeceras en las solicitudes
                .allowCredentials(true); // Permite el envío de cookies o cabeceras de autorización
    }
}