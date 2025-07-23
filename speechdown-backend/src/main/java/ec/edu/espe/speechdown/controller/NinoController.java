package ec.edu.espe.speechdown.controller;

import ec.edu.espe.speechdown.model.Nino;
import ec.edu.espe.speechdown.repository.NinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ninos")
@CrossOrigin(origins = "http://localhost:3000") // Permitir acceso desde tu frontend
public class NinoController {

    @Autowired
    private NinoRepository ninoRepo;

    @GetMapping
    public List<Nino> listarNinos() {
        return ninoRepo.findAll();
    }

    @PostMapping
    public Nino crearNino(@RequestBody Nino nino) {
        return ninoRepo.save(nino);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Nino> actualizarNino(@PathVariable Long id, @RequestBody Nino ninoData) {
        return ninoRepo.findById(id)
                .map(n -> {
                    n.setNombre(ninoData.getNombre());
                    n.setEdad(ninoData.getEdad());
                    return ResponseEntity.ok(ninoRepo.save(n));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void eliminarNino(@PathVariable Long id) {
        ninoRepo.deleteById(id);
    }
}
