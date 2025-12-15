// ==========================================
// 4. ALGORITMOS DE ORDENAMIENTO
// ==========================================
public class Ordenador {
    // Algoritmo Bubble Sort para ordenar listas visuales por Nombre
    // Análisis: O(n^2). No es el más eficiente, pero fácil de implementar para listas pequeñas de UI.
    public static void ordenarPorNombre(java.util.List<Paciente> lista) {
        int n = lista.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (lista.get(j).nombre.compareToIgnoreCase(lista.get(j + 1).nombre) > 0) {
                    Paciente temp = lista.get(j);
                    lista.set(j, lista.get(j + 1));
                    lista.set(j + 1, temp);
                }
            }
        }
    }
}
