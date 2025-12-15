// ==========================================
// 3. GESTIÓN DE PERSISTENCIA (.txt)
// ==========================================
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Persistencia {
    private static final String FILE_PACIENTES = "pacientes.txt";
    private static final String FILE_MEDICOS = "medicos.txt";
    private static final String SEP = ";"; // Separador

    // Análisis de Algoritmo: O(n) donde n es el número de nodos. Recorre todo para guardar.
    public static void guardarDatos(ArbolPacientes arbol, TablaHashMedicos hashMedicos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PACIENTES))) {
            java.util.List<Paciente> lista = new java.util.ArrayList<>();
            arbol.llenarLista(lista);
            for (Paciente p : lista) {
                // Formato: ID;Nombre;RutaP;NomTutor;RutaT;CI;Dom;Fecha;Raza
                String linea = String.join(SEP, p.id, p.nombre, p.rutaFotoPaciente, p.nombreTutor, 
                        p.rutaFotoTutor, p.ciTutor, p.domicilio, p.fechaNacimiento.toString(), p.raza);
                writer.write(linea);
                writer.newLine();
                
                // Guardar historial debajo del paciente con prefijo H



Nodo<DetalleHistorial> actual = p.historial.entradas.cabeza;
                while(actual != null) {
                    writer.write("H" + SEP + actual.dato.detalle + SEP + actual.dato.fecha);
                    writer.newLine();
                    actual = actual.siguiente;
                }
            }
        } catch (IOException e) { e.printStackTrace(); }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_MEDICOS))) {
            for (Medico m : hashMedicos.obtenerTodos()) {
                String linea = String.join(SEP, m.nombre, m.ci, m.fechaNacimiento, m.matricula, m.especialidad, m.rutaFoto);
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static void cargarDatos(ArbolPacientes arbol, TablaHashMedicos hashMedicos) {
        File fp = new File(FILE_PACIENTES);
        if (fp.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(fp))) {
                String linea;
                Paciente ultimoPaciente = null;
                while ((linea = br.readLine()) != null) {
                    String[] parts = linea.split(SEP);
                    if (parts[0].equals("H") && ultimoPaciente != null) {
                        ultimoPaciente.historial.entradas.agregar(new DetalleHistorial(parts[1], parts[2]));
                    } else {
                        if(parts.length >= 9) {
                            ultimoPaciente = new Paciente(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7], parts[8]);
                            arbol.insertar(ultimoPaciente);
                        }
                    }
                }
            } catch (IOException e) { e.printStackTrace(); }
        }

        File fm = new File(FILE_MEDICOS);
        if(fm.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(fm))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] parts = linea.split(SEP);
                    if(parts.length >= 6) {
                        hashMedicos.insertar(new Medico(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]));
                    }
                }
            } catch (IOException e) { e.printStackTrace(); }
        }
    }
}