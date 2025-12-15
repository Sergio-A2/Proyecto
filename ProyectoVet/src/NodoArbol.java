import java.io.Serializable;

public class NodoArbol implements Serializable {
    Paciente dato;
    NodoArbol izquierdo, derecho;

    public NodoArbol(Paciente dato) {
        this.dato = dato;
        this.izquierdo = this.derecho = null;
    }

}
