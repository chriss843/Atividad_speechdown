package ec.edu.espe.speechdown.dto;

public class ActividadResponse {
    private String actividad;

    public ActividadResponse(String actividad) {
        this.actividad = actividad;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }
}