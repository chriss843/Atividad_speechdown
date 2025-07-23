package ec.edu.espe.speechdown.repository;

import ec.edu.espe.speechdown.model.Historial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistorialRepository extends JpaRepository<Historial, Long> {
    List<Historial> findByNinoId(Long ninoId);
}