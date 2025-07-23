package ec.edu.espe.speechdown.controller;

import ec.edu.espe.speechdown.dto.ActividadRequest;
import ec.edu.espe.speechdown.service.ActividadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/actividades")
public class ActividadController {

    private final ActividadService actividadService;

    public ActividadController(ActividadService actividadService) {
        this.actividadService = actividadService;
    }

    @PostMapping("/generar")
    public ResponseEntity<Map<String, String>> generarActividad(@RequestBody ActividadRequest request) {
        String actividad = actividadService.generarActividad(request);
        Map<String, String> response = new HashMap<>();

        if (actividad.startsWith("El servicio de generación") || actividad.startsWith("Error al conectar")) {
            response.put("error", actividad);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        } else if (actividad.startsWith("Ocurrió un error inesperado")) {
            response.put("error", actividad);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } else {
            response.put("actividad", actividad);
            return ResponseEntity.ok(response);
        }
    }
}