package ec.edu.espe.speechdown.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "progreso")
public class Progreso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "nino_id", nullable = false)
    private Nino nino;

    private int totalCuentos = 0;
    private int totalActividades = 0;
    private LocalDateTime ultimaActividad;

    // Getters y Setters


    public Progreso() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Nino getNino() {
        return nino;
    }

    public void setNino(Nino nino) {
        this.nino = nino;
    }

    public int getTotalCuentos() {
        return totalCuentos;
    }

    public void setTotalCuentos(int totalCuentos) {
        this.totalCuentos = totalCuentos;
    }

    public int getTotalActividades() {
        return totalActividades;
    }

    public void setTotalActividades(int totalActividades) {
        this.totalActividades = totalActividades;
    }

    public LocalDateTime getUltimaActividad() {
        return ultimaActividad;
    }

    public void setUltimaActividad(LocalDateTime ultimaActividad) {
        this.ultimaActividad = ultimaActividad;
    }
}
