package ec.edu.espe.speechdown.controller;

import ec.edu.espe.speechdown.model.Historial;
import ec.edu.espe.speechdown.repository.HistorialRepository;
import ec.edu.espe.speechdown.repository.NinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historial")
@CrossOrigin(origins = "http://localhost:3000")
public class HistorialController {

    @Autowired
    private HistorialRepository historialRepo;

    @Autowired
    private NinoRepository ninoRepo;

    @PostMapping
    public Historial registrarHistorial(@RequestBody Historial historial) {
        return historialRepo.save(historial);
    }

    @GetMapping("/{ninoId}")
    public List<Historial> obtenerHistorialPorNino(@PathVariable Long ninoId) {
        return historialRepo.findByNinoId(ninoId);
    }
}
