package ec.edu.espe.speechdown.dto;

public class AuthRespuesta {
    private String mensaje;
    private String rol;

    public AuthRespuesta(String mensaje, String token, String rol) {
        this.mensaje = mensaje;
        this.rol = rol;
    }

    // Getters y Setters
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}