package ec.edu.espe.speechdown.dto;

import java.util.List;

public class ActividadRequest {
    private String nombre;
    private List<String> categorias;
    private int edad; // Nueva propiedad para la edad del niño

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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}