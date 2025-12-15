import java.io.Serializable;

public class ListaEnlazada<T> implements Serializable {
    Nodo<T> cabeza;
    int size;

    // Análisis de Algoritmo: Inserción al final O(n), al inicio O(1). 
    // Usamos inserción al final para mantener orden cronológico en historial.
    public void agregar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo<T> temp = cabeza;
            while (temp.siguiente != null) {
                temp = temp.siguiente;
            }
            temp.siguiente = nuevo;
        }
        size++;
    }

    public T obtener(int index) {
        if (index < 0 || index >= size) return null;
        Nodo<T> temp = cabeza;
        for (int i = 0; i < index; i++) {
            temp = temp.siguiente;
        }
        return temp.dato;
    }
}
