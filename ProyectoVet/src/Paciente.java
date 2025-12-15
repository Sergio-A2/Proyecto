import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public class Paciente implements Serializable, Comparable<Paciente> {
    String id; // Autogenerado
    String nombre;
    String rutaFotoPaciente;
    String nombreTutor;
    String rutaFotoTutor;
    String ciTutor;
    String domicilio;
    LocalDate fechaNacimiento;
    int edad; // Autogenerada
    String raza;
    Historial historial;

    public Paciente(String nombre, String rutaFotoP, String nombreTutor, String rutaFotoT, String ciTutor, String domicilio, LocalDate fechaNac, String raza) {
        this.id = "P-" + System.currentTimeMillis() % 10000; // ID Simple autogenerado
        this.nombre = nombre;
        this.rutaFotoPaciente = rutaFotoP;
        this.nombreTutor = nombreTutor;
        this.rutaFotoTutor = rutaFotoT;
        this.ciTutor = ciTutor;
        this.domicilio = domicilio;
        this.fechaNacimiento = fechaNac;
        this.raza = raza;
        this.historial = new Historial();
        calcularEdad();
    }
    
    // Constructor para carga
    public Paciente(String id, String nombre, String rutaP, String nomT, String rutaT, String ciT, String dom, String fecha, String raza) {
        this.id = id;
        this.nombre = nombre;
        this.rutaFotoPaciente = rutaP;
        this.nombreTutor = nomT;
        this.rutaFotoTutor = rutaT;
        this.ciTutor = ciT;
        this.domicilio = dom;
        this.fechaNacimiento = LocalDate.parse(fecha);
        this.raza = raza;
        this.historial = new Historial();
        calcularEdad();
    }
    
    public Paciente() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRutaFotoPaciente() {
		return rutaFotoPaciente;
	}

	public void setRutaFotoPaciente(String rutaFotoPaciente) {
		this.rutaFotoPaciente = rutaFotoPaciente;
	}

	public String getNombreTutor() {
		return nombreTutor;
	}

	public void setNombreTutor(String nombreTutor) {
		this.nombreTutor = nombreTutor;
	}

	public String getRutaFotoTutor() {
		return rutaFotoTutor;
	}

	public void setRutaFotoTutor(String rutaFotoTutor) {
		this.rutaFotoTutor = rutaFotoTutor;
	}

	public String getCiTutor() {
		return ciTutor;
	}

	public void setCiTutor(String ciTutor) {
		this.ciTutor = ciTutor;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public Historial getHistorial() {
		return historial;
	}

	public void setHistorial(Historial historial) {
		this.historial = historial;
	}

	public void calcularEdad() {
        if (fechaNacimiento != null) {
            this.edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();
        }
    }

    public int compareTo(Paciente o) {
        return this.id.compareTo(o.id);
    }
}
