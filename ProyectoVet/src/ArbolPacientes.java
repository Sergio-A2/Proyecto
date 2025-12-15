
public class ArbolPacientes {
    NodoArbol raiz;

    // Análisis de Algoritmo: Inserción promedio O(log n), peor caso O(n) si no está balanceado.
    // RECURSIVIDAD utilizada aquí.
    public void insertar(Paciente paciente) {
        raiz = insertarRec(raiz, paciente);
    }

    private NodoArbol insertarRec(NodoArbol raiz, Paciente paciente) {
        if (raiz == null) {
            raiz = new NodoArbol(paciente);
            return raiz;
        }
        if (paciente.id.compareTo(raiz.dato.id) < 0)
            raiz.izquierdo = insertarRec(raiz.izquierdo, paciente);
        else if (paciente.id.compareTo(raiz.dato.id) > 0)
            raiz.derecho = insertarRec(raiz.derecho, paciente);
        
        return raiz;
    }

    // Análisis de Algoritmo: Búsqueda binaria O(log n).
    public Paciente buscarPorId(String id) {
        return buscarRec(raiz, id);
    }

    private Paciente buscarRec(NodoArbol raiz, String id) {
        if (raiz == null || raiz.dato.id.equals(id))
            return (raiz != null) ? raiz.dato : null;
        
        if (id.compareTo(raiz.dato.id) < 0)
            return buscarRec(raiz.izquierdo, id);
        
        return buscarRec(raiz.derecho, id);
    }

    // Para visualizar lista completa (InOrder Traversal)
    public void llenarLista(java.util.List<Paciente> lista) {
        inOrderRec(raiz, lista);
    }

    private void inOrderRec(NodoArbol raiz, java.util.List<Paciente> lista) {
        if (raiz != null) {
            inOrderRec(raiz.izquierdo, lista);
            lista.add(raiz.dato);
            inOrderRec(raiz.derecho, lista);
        }
    }
}
