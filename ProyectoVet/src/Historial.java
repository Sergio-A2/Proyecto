import java.io.Serializable;

public class Historial implements Serializable {
    // USO DE LISTAS ENLAZADAS (Propia implementación abajo) para los detalles
    ListaEnlazada<DetalleHistorial> entradas;

    public Historial() {
        this.entradas = new ListaEnlazada<>();
    }
}
