package proyecto_final;

import java.time.LocalDate;

public class Alumno {
	private int numMatricula;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private Curso curso;
	private LocalDate fechaNacimiento;
	
	public Alumno(int numMatricula, String nombre, String apellido1, String apellido2, Curso curso,
			LocalDate fechaNacimiento) {
		super();
		this.numMatricula = numMatricula;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.curso = curso;
		this.fechaNacimiento = fechaNacimiento;
	}

	public int getNumMatricula() {
		return numMatricula;
	}

	public void setNumMatricula(int numMatricula) {
		this.numMatricula = numMatricula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
}