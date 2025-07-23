package ec.edu.espe.speechdown.dto;

import java.util.List;

public class CuentoRequest {
    private String nombre;
    private List<String> categorias;

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<String> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<String> categorias) {
        this.categorias = categorias;
    }
}