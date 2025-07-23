package ec.edu.espe.speechdown.service;

import ec.edu.espe.speechdown.model.Progreso;
import ec.edu.espe.speechdown.repository.ProgresoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgresoService {

    private final ProgresoRepository progresoRepository;

    public ProgresoService(ProgresoRepository progresoRepository) {
        this.progresoRepository = progresoRepository;
    }

    public List<Progreso> listarProgreso() {
        return progresoRepository.findAll();
    }
}