package ec.edu.espe.speechdown.dto;

public class CuentoResponse {
    private String cuento;

    // Constructor
    public CuentoResponse(String cuento) {
        this.cuento = cuento;
    }

    // Getter y Setter
    public String getCuento() {
        return cuento;
    }

    public void setCuento(String cuento) {
        this.cuento = cuento;
    }
}