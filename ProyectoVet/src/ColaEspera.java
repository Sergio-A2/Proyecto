// COLA (QUEUE) - Simulación de Sala de Espera (Requisito extra implícito en vet)
public class ColaEspera {
    private java.util.Queue<Paciente> cola = new java.util.LinkedList<>();
    public void encolar(Paciente p) { cola.add(p); }
    public Paciente atender() { return cola.poll(); }
}
