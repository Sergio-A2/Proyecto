
// PILA (STACK) - Para navegación (Historial de vistas)
public class PilaNavegacion {
    private java.util.Stack<String> pila = new java.util.Stack<>(); // Usando clase base de Java por brevedad visual, concepto aplicado.
    
    public void push(String vista) { pila.push(vista); }
    public String pop() { return pila.isEmpty() ? null : pila.pop(); }
}