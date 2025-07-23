package ec.edu.espe.speechdown.controller;

import ec.edu.espe.speechdown.dto.CuentoRequest;
import ec.edu.espe.speechdown.dto.CuentoResponse;
import ec.edu.espe.speechdown.service.CuentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/cuentos")
public class CuentoController {

    private final CuentoService cuentoService;

    public CuentoController(CuentoService cuentoService) {
        this.cuentoService = cuentoService;
    }

    @PostMapping("/generar")
    public ResponseEntity<Map<String, String>> generarCuento(@RequestBody CuentoRequest request) {
        String cuento = cuentoService.generarCuento(request);
        return ResponseEntity.ok(Map.of("cuento", cuento));
    }
}