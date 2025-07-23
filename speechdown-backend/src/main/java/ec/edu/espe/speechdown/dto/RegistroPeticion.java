package ec.edu.espe.speechdown.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegistroPeticion {

    @NotBlank(message = "El email no puede estar vacío.")
    @Email(message = "El email debe ser válido.")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía.")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres.")
    private String contrasena;

    @NotBlank(message = "El rol no puede estar vacío.")
    private String rol; // Por ejemplo: "TERAPEUTA", "PADRE"

    // Getters y Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}