import java.io.Serializable;
import java.time.LocalDate;

public class DetalleHistorial implements Serializable {
    String detalle;
    String fecha;

    public DetalleHistorial(String detalle) {
        this.detalle = detalle;
        this.fecha = LocalDate.now().toString();
    }
    // Constructor para carga desde TXT
    public DetalleHistorial(String detalle, String fecha) {
        this.detalle = detalle;
        this.fecha = fecha;
    }
}
