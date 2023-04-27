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
	/*
	public ArrayList<Alumno> getBooks(String autor){
		ArrayList<Alumno> books = null;
		try {
			PreparedStatement ps = this.connection.
					prepareStatement("SELECT id,titulo,"
							+ "ano,autor,editorial FROM books WHERE autor=?");
			ps.setString(1,autor);
			ResultSet rs = ps.executeQuery();
			books = new ArrayList<Alumno>();
			while(rs.next()) {
				books.add(new Alumno(rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5)));
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return books;
	}
	public ArrayList<Alumno> getBooks(String autor, String editorial){
		ArrayList<Book> books = null;
		try {
			PreparedStatement ps = this.connection.
					prepareStatement("SELECT id,titulo,"
							+ "ano,autor,editorial FROM books WHERE autor=?"
							+ " AND editorial=?");
			ps.setString(1,autor);
			ps.setString(2, editorial);
			ResultSet rs = ps.executeQuery();
			books = new ArrayList<Book>();
			while(rs.next()) {
				books.add(new Book(rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5)));
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return books;
	}

	public void getData(String source, ArrayList<String> fields,
			HashMap<String,Object> filter) {

	}
	/
	 * 
	 * @param filter
	 * @return

	public ArrayList<Book> getBooks(HashMap<String,Object> filter){
		ArrayList<Book> books = null;
		int i=0, type=Types.VARCHAR;
		String whereData="";
		try {
			for(String key:filter.keySet()) {
				whereData+=key+"=? AND ";
			}
			//" titulo=? AND editorial=? AND "
			whereData = whereData.substring(0, whereData.length()-5);
			PreparedStatement ps = this.connection.
					prepareStatement("SELECT id,titulo,"
							+ "ano,autor,editorial FROM books WHERE " +
							whereData);

			for(Object value:filter.values()) {
				if(value instanceof Integer) {
					type = Types.INTEGER;
				}else if(value instanceof Float) {
					type = Types.FLOAT;
				}else if(value instanceof Double) {
					type = Types.DOUBLE;
				}else if(value instanceof String) {
					type = Types.VARCHAR;
				}
				ps.setObject(++i, value, type);				
			}

			ResultSet rs = ps.executeQuery();
			books = new ArrayList<Book>();
			while(rs.next()) {
				books.add(new Book(rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5)));
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return books;
	 */


}