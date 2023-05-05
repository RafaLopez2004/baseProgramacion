package proyecto_final;

import java.sql.CallableStatement;
import java.sql.Connection;
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
	private Connection connection=null;
	private Statement statement=null;

	/**
	 * Constructor especializado en inicializar objetos
	 * de tipo DatabaseManager a partir de un objeto de conexión
	 * que no puede ser nulo
	 * @param connection Objeto de conexión
	 */
	public DatabaseManager(@NonNull Connection connection) {
		this.connection = connection;
		try {
			this.statement = connection.createStatement();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	/**
	 * Metodo sobrecargado que se encarga de extraer alumnos 
	 * de la base de datos a partir de ningun parametro, el curso
	 * o la letra del curso y el curso
	 * @return alumnos Los alumnos obtenidos segun la consulta
	 */
	/* TODO Los alumnos poseen una propiedad curso, que es una clave foranea de otra tabla y 
	 * una propiedad que es un objeto, averiguar en casa una forma de obtener el objeto completo 
	 * traves de la consulta */
	public ArrayList<Alumno> getAlumnos(){
		ArrayList<Alumno> alumnos = null;
		try {
			PreparedStatement ps = this.connection.
					prepareStatement("SELECT numMatricula,nombre,apellido1, apellido2, curso, fechaNacimiento FROM alumnos");
			ResultSet rs = ps.executeQuery();
			alumnos = new ArrayList<Alumno>();
			while(rs.next()) {
				alumnos.add(new Alumno(rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getDate(6))); 
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return alumnos;
	}
	
	public ArrayList<Alumno> getAlumnosAño(Curso curso){
		ArrayList<Alumno> alumnos = null;
		try {
			PreparedStatement ps = this.connection.
					prepareStatement("SELECT * "
							+ "FROM vista1  "
							+ "WHERE año=?");
			ps.setInt(1,curso.getAño());
			ResultSet rs = ps.executeQuery();
			alumnos = new ArrayList<Alumno>();
			/*while(rs.next()) {
			
			}
				alumnos.add(new Alumno(rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5)));
			}*/
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return alumnos;
	}
	public ArrayList<Alumno> getAlumnosLetraAño(Curso curso){
		ArrayList<Alumno> alumnos = null;
		try {
			PreparedStatement ps = this.connection.
					prepareStatement("SELECT * "
							+ "FROM vista1  "
							+ "WHERE año=? "
							+ "AND letraAño=?");
			ps.setInt(1,curso.getAño());
			ps.setString(2, curso.getLetraAño());
			ResultSet rs = ps.executeQuery();
			alumnos = new ArrayList<Alumno>();
			
			while(rs.next()) {	
				alumnos.add(new Alumno(rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						curso,
						rs.getDate(6))); 
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return alumnos;
	}

}