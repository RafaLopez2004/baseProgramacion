package proyecto_final;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import org.eclipse.jdt.annotation.NonNull;

public class DatabaseManager {
	private DatabaseConnection databaseConnection=null;
	private Statement statement=null;

	/**
	 * Constructor especializado en inicializar objetos
	 * de tipo DatabaseManager a partir de un objeto de conexión
	 * que no puede ser nulo
	 * @param connection Objeto de conexión
	 */
	public DatabaseManager(@NonNull DatabaseConnection connection) {
		this.databaseConnection = connection;
	}

	/**
	 * Metodo sobrecargado que se encarga de extraer alumnos 
	 * de la base de datos a partir de ningun parametro, el curso
	 * o la letra del curso y el curso
	 * @return alumnos Los alumnos obtenidos segun la consulta
	 */
	public ArrayList<Alumno> getAlumnos(){
		ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(this.databaseConnection.getConnectionString());
			PreparedStatement ps = connection.
					prepareStatement("SELECT numMatricula,nombre,apellido1, apellido2 , fechaNacimientoid,id,año,letraAño FROM vista1");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				alumnos.add(this.instanciarAlumno(rs));
			} 
		} catch (SQLException e) {			
			e.printStackTrace();
		} finally {
			this.databaseConnection.disconnect();
		}
		return alumnos;
	}

	public ArrayList<Alumno> getAlumnos(int curso){
		ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
		String sqlQuery = "SELECT numMatricula,nombre,apellido1, apellido2 , fechaNacimientoid,id,año,letraAño "
				+ "FROM vista1  "
				+ "WHERE año=?";
		Connection connection;
		try {
			connection = DriverManager.getConnection(this.databaseConnection.getConnectionString());
			PreparedStatement ps = connection.prepareStatement(sqlQuery);
			ps.setInt(1,curso);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				alumnos.add(this.instanciarAlumno(rs));
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return alumnos;
	}

	public ArrayList<Alumno> getAlumnos(String letraAño){
		ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
		Connection connection;
		try {
			connection = DriverManager.getConnection(this.databaseConnection.getConnectionString());
			PreparedStatement ps = connection.
					prepareStatement("SELECT numMatricula,nombre,apellido1, apellido2 ,id, fechaNacimientoid,año,letraAño "
							+ "FROM vista1  "
							+ "WHERE letraAño=?");
			ps.setString(1,letraAño);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				alumnos.add(this.instanciarAlumno(rs));
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		} finally {
			this.databaseConnection.disconnect();
		}
		return alumnos;
	}

	public ArrayList<Alumno> getAlumnos(Curso curso){
		ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
		Connection connection;
		try {
			connection = DriverManager.getConnection(this.databaseConnection.getConnectionString());
			PreparedStatement ps = connection.
					prepareStatement("SELECT numMatricula,nombre,apellido1, apellido2 , fechaNacimiento "
							+ "FROM vista1  "
							+ "WHERE año=? "
							+ "AND letraAño=?");
			ps.setInt(1,curso.getAño());
			ps.setString(2, curso.getLetraAño());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {	
				alumnos.add(this.instanciarAlumno(rs, curso));
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		} finally {
			this.databaseConnection.disconnect();
		}
		return alumnos;
	}
/**
 * 
 * @return
 */
	public ArrayList<Curso> getCursos(){
		ArrayList<Curso> cursos = new ArrayList<Curso>();
		Connection connection;
		try {
			connection = DriverManager.getConnection(this.databaseConnection.getConnectionString());
			PreparedStatement ps = connection.
					prepareStatement("SELECT id,año,letraAño FROM Curso");
			ResultSet rs = ps.executeQuery();
			cursos = this.instanciarCursos(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.databaseConnection.disconnect();
		}
		return cursos;
	}

	public ArrayList<Curso> getCursos(int año){
		ArrayList<Curso> cursos = new ArrayList<Curso>();
		Connection connection;
		try {
			connection = DriverManager.getConnection(this.databaseConnection.getConnectionString());
			PreparedStatement ps = connection.
					prepareStatement("SELECT id,año,letraAño "
							+ "FROM Curso "
							+ "WHERE año =?");
			ps.setInt(1,año);
			ResultSet rs = ps.executeQuery();
			cursos = this.instanciarCursos(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.databaseConnection.disconnect();
		}
		return cursos;
	}

	public ArrayList<Curso> getCursos(String letraAño){
		ArrayList<Curso> cursos = new ArrayList<Curso>();
		Connection connection;
		try {
			connection = DriverManager.getConnection(this.databaseConnection.getConnectionString());
			PreparedStatement ps = connection.
					prepareStatement("SELECT id,año,letraAño "
							+ "FROM Curso "
							+ "WHERE letraAño =?");
			ps.setString(1,letraAño);
			ResultSet rs = ps.executeQuery();
			cursos = this.instanciarCursos(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.databaseConnection.disconnect();
		}
		return cursos;
	}

	public ArrayList<Curso> getCursos(int año, String letraAño){
		ArrayList<Curso> cursos = new ArrayList<Curso>();
		Connection connection;
		try {
			connection = DriverManager.getConnection(this.databaseConnection.getConnectionString());
			PreparedStatement ps = connection.
					prepareStatement("SELECT id,año,letraAño "
							+ "FROM Curso "
							+ "WHERE letraAño =? "
							+ "AND año =?");
			ps.setString(1,letraAño);
			ps.setInt(2, año);
			ResultSet rs = ps.executeQuery();
			cursos = this.instanciarCursos(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.databaseConnection.disconnect();
		}
		return cursos;
	}
	public ArrayList<Profesor> getProfesores(){
		ArrayList<Profesor> profesores = new ArrayList<Profesor>();
		Connection connection;
		try {
			connection = DriverManager.getConnection(this.databaseConnection.getConnectionString());
			PreparedStatement ps = connection.
					prepareStatement("SELECT idProfesor,nombre,apellido1, apellido2 , fechaNacimiento,id,año,letraAño FROM vista2 ");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				profesores.add(this.instanciarProfesor(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return profesores;
	}
	public ArrayList<Profesor> getProfesores(String letraAño){
		ArrayList<Profesor> profesores = new ArrayList<Profesor>();
		Connection connection;
		try {
			connection = DriverManager.getConnection(this.databaseConnection.getConnectionString());
			PreparedStatement ps = connection.
					prepareStatement("SELECT idProfesor,nombre,apellido1, apellido2 , fechaNacimiento,id,año,letraAño "
							+ "FROM vista2  "
							+ "WHERE letraAño=?");
			ps.setString(1,letraAño);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				profesores.add(this.instanciarProfesor(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.databaseConnection.disconnect();
		}
		return profesores;
	}
	
	public ArrayList<Profesor> getProfesores(Curso curso){
		ArrayList<Profesor> profesores = new ArrayList<Profesor>();
		Connection connection;
		try {
			connection = DriverManager.getConnection(this.databaseConnection.getConnectionString());
			PreparedStatement ps = connection.
					prepareStatement("SELECT idProfesor,nombre,apellido1, apellido2 , fechaNacimiento,id,año,letraAño "
							+ "FROM vista2  "
							+ "WHERE letraAño=? "
							+ "WHERE año=?");
			ps.setString(1,curso.getLetraAño());
			ps.setInt(2, curso.getAño());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				profesores.add(this.instanciarProfesor(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.databaseConnection.disconnect();
		}
		return profesores;
	}
	
	
	/**
	 * Metodos helper que realizan ciertas acciones que se repiten varias veces a lo largo del codigo
	 */
	private Alumno instanciarAlumno(ResultSet rs) {
		Alumno alumno = null;
		Curso curso = this.instanciarCurso(rs);	
		alumno = this.instanciarAlumno(rs, curso);
		return alumno;
	}
	private Alumno instanciarAlumno(ResultSet rs, Curso curso) {
		Alumno alumno = null;
		try {
			alumno = new Alumno(rs.getInt(1),
					rs.getString(2),
					rs.getString(3),
					rs.getString(4),
					curso,
					rs.getDate(5));
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return alumno;
	}
	private Curso instanciarCurso(ResultSet rs) {
		Curso curso = null;
		try {
			if (rs.getFetchSize() > 3) {
				curso = new Curso(
						rs.getInt(rs.getFetchSize()-2),
						rs.getInt(rs.getFetchSize()-1),
						rs.getString(rs.getFetchSize()));
			} else {
				curso = new Curso(
						rs.getInt(1),
						rs.getInt(2),
						rs.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return curso;
	}
	private ArrayList<Curso> instanciarCursos(ResultSet rs) {
		ArrayList<Curso> cursos = new ArrayList<Curso>();
		try {
			while(rs.next()) {	
				cursos.add(this.instanciarCurso(rs));
			}
			for(Curso curso:cursos) {
				ArrayList<Alumno> alumnos = this.getAlumnos(curso);
				curso.setAlumnos(alumnos);
				ArrayList<Profesor> profesores = this.getProfesores(curso);
				curso.setProfesores(profesores);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cursos;
	}
	private Profesor instanciarProfesor(ResultSet rs) {
		Profesor profesor = null;
		Curso curso = this.instanciarCurso(rs);	
		profesor = this.instanciarProfesor(rs, curso);
		return profesor;
	}
	private Profesor instanciarProfesor(ResultSet rs, Curso curso) {
		Profesor profesor = null;
		try {
			profesor = new Profesor(rs.getInt(1),
					rs.getString(2),
					rs.getString(3),
					rs.getString(4),
					rs.getDate(5), 
					curso);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return profesor;
	}
}
