package ec.edu.espe.speechdown.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "historial")
public class Historial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "nino_id", nullable = false)
    private Nino nino;

    private String tipo; // "cuento" o "actividad"

    @ElementCollection
    @CollectionTable(name = "historial_categorias", joinColumns = @JoinColumn(name = "historial_id"))
    @Column(name = "categoria")
    private List<String> categorias;

    @Column(columnDefinition = "TEXT")
    private String contenido;

    private LocalDateTime fecha = LocalDateTime.now();

    // Getters y Setters

    public Historial() {
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<String> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<String> categorias) {
        this.categorias = categorias;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
