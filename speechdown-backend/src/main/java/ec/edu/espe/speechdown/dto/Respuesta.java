package ec.edu.espe.speechdown.dto;

public class Respuesta {
    private String mensaje;
    private String rol;

    public Respuesta() {
    }

    public Respuesta(String mensaje, String rol) {
        this.mensaje = mensaje;
        this.rol = rol;
    }

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
