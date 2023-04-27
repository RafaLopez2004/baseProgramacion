package proyecto_final;

import java.util.ArrayList;

public class Curso {
	private int id;
	private int año;
	private String letraAño;
	private ArrayList<Profesor> profesores;
	private ArrayList<Alumno> alumnos;
	
	public Curso(int id, int año, String letraAño) {
		this.id = id;
		this.año = año;
		this.letraAño = letraAño;
		this.profesores = new ArrayList<Profesor>();
		this.alumnos = new ArrayList<Alumno>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAño() {
		return año;
	}

	public void setAño(int año) {
		this.año = año;
	}

	public String getLetraAño() {
		return letraAño;
	}

	public void setLetraAño(String letraAño) {
		this.letraAño = letraAño;
	}

	public ArrayList<Profesor> getProfesores() {
		return profesores;
	}

	public void setProfesores(ArrayList<Profesor> profesores) {
		this.profesores = profesores;
	}
	
	public void addProfesor(Profesor profesor) {
		this.profesores.add(profesor);
	}

	public ArrayList<Alumno> getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(ArrayList<Alumno> alumnos) {
		this.alumnos = alumnos;
	}
	
	public void addAlumno(Alumno alumno) {
		this.alumnos.add(alumno);
	}
}