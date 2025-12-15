import java.io.Serializable;

public class Nodo<T> implements Serializable {
    T dato;
    Nodo<T> siguiente;

    public Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}
