package ec.edu.espe.speechdown.controller;

import ec.edu.espe.speechdown.model.Progreso;
import ec.edu.espe.speechdown.repository.NinoRepository;
import ec.edu.espe.speechdown.repository.ProgresoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/progreso")
@CrossOrigin(origins = "http://localhost:3000")
public class ProgresoController {

    @Autowired
    private ProgresoRepository progresoRepo;
    @Autowired
    private NinoRepository ninoRepo;

    @GetMapping
    public ResponseEntity<?> obtenerTodosLosProgresos() {
        return ResponseEntity.ok(progresoRepo.findAll());
    }

    @GetMapping("/{ninoId}")
    public ResponseEntity<Progreso> obtenerProgreso(@PathVariable Long ninoId) {
        return progresoRepo.findByNinoId(ninoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/actualizar/{ninoId}")
    public ResponseEntity<Progreso> actualizarProgreso(
            @PathVariable Long ninoId, @RequestParam String tipo) {

        Progreso progreso = progresoRepo.findByNinoId(ninoId)
                .orElseGet(() -> {
                    Progreso nuevo = new Progreso();
                    nuevo.setNino(ninoRepo.findById(ninoId).orElseThrow());
                    return nuevo;
                });

        if (tipo.equals("cuento")) {
            progreso.setTotalCuentos(progreso.getTotalCuentos() + 1);
        } else if (tipo.equals("actividad")) {
            progreso.setTotalActividades(progreso.getTotalActividades() + 1);
        }
        progreso.setUltimaActividad(LocalDateTime.now());

        return ResponseEntity.ok(progresoRepo.save(progreso));
    }
}

