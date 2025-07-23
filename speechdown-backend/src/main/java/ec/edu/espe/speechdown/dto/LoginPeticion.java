package ec.edu.espe.speechdown.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginPeticion {

    @NotBlank(message = "El email no puede estar vacío.")
    @Email(message = "El email debe ser válido.")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía.")
    private String contrasena;

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
}