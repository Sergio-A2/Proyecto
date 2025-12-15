
public class TablaHashMedicos {
    private final int TAMANO = 100;
    // USO DE ARRAYS para los buckets
    private ListaEnlazada<Medico>[] tabla;

    @SuppressWarnings("unchecked")
    public TablaHashMedicos() {
        tabla = new ListaEnlazada[TAMANO];
        for (int i = 0; i < TAMANO; i++) {
            tabla[i] = new ListaEnlazada<>();
        }
    }

    // Función Hash simple
    private int hash(String key) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = (31 * hash + key.charAt(i)) % TAMANO;
        }
        return Math.abs(hash);
    }

    // Análisis de Algoritmo: Inserción O(1) promedio.
    public void insertar(Medico medico) {
        int index = hash(medico.ci);
        tabla[index].agregar(medico);
    }

    // Obtener todos para listar
    public java.util.List<Medico> obtenerTodos() {
        java.util.List<Medico> lista = new java.util.ArrayList<>();
        for(int i=0; i<TAMANO; i++) {
            Nodo<Medico> temp = tabla[i].cabeza;
            while(temp != null) {
                lista.add(temp.dato);
                temp = temp.siguiente;
            }
        }
        return lista;
    }
}
