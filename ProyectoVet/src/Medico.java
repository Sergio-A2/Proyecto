import java.io.Serializable;

public class Medico implements Serializable {
    String nombre;
    String ci; // Key para Hash
    String fechaNacimiento;
    String matricula;
    String especialidad;
    String rutaFoto;

    public Medico(String nombre, String ci, String fechaNac, String matricula, String especialidad, String rutaFoto) {
        this.nombre = nombre;
        this.ci = ci;
        this.fechaNacimiento = fechaNac;
        this.matricula = matricula;
        this.especialidad = especialidad;
        this.rutaFoto = rutaFoto;
    }

	public Medico() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCi() {
		return ci;
	}

	public void setCi(String ci) {
		this.ci = ci;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public String getRutaFoto() {
		return rutaFoto;
	}

	public void setRutaFoto(String rutaFoto) {
		this.rutaFoto = rutaFoto;
	}
    
    
}