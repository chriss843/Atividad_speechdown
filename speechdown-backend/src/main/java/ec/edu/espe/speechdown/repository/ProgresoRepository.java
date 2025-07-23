package ec.edu.espe.speechdown.repository;

import ec.edu.espe.speechdown.model.Progreso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProgresoRepository extends JpaRepository<Progreso, Long> {
    Optional<Progreso> findByNinoId(Long ninoId);
}