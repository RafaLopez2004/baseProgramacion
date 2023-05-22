package proyecto_final;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class DatabaseManager {
	private static final String NUM_MATRICULA = "numMatricula";
	private static final String NOMBRE = "nombre";
	private static final String APELLIDO_1 = "apellido1";
	private static final String APELLIDO_2 = "apellido2";
	private static final String CURSO = "curso";
	private static final String FECHA_NACIMIENTO = "fechaNacimiento";
	private static final String ID_PROFESOR = "idProfesor";
	private static final String ID = "id";
	private static final String AÑO = "año";
	private static final String LETRA_AÑO = "letraAño";
	private DatabaseConnection databaseConnection=null;
	private Statement statement=null;
	private Connection connection;

	private ColumnOrder order;
	/**
	 * Constructor especializado en inicializar objetos
	 * de tipo DatabaseManager a partir de un objeto de conexión
	 * que no puede ser nulo
	 * @param connection Objeto de conexión
	 */
	public DatabaseManager(@NotNull DatabaseConnection connection) {
		this.databaseConnection = connection;
	}

	public void setOrder(ColumnOrder order) {
		this.order = order;
	}

	/**
	 * Metodo sobrecargado que se encarga de extraer alumnos 
	 * de la base de datos a partir de ningun parametro, el curso
	 * o la letra del curso y el curso
	 * @return alumnos Los alumnos obtenidos segun la consulta
	 */
	public ArrayList<Alumno> getAlumnos(){
		ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
		try {
			connection = DriverManager.getConnection(this.databaseConnection.getConnectionString());
			PreparedStatement ps = connection.
					prepareStatement("SELECT numMatricula,nombre,apellido1, apellido2 , fechaNacimiento,id,año,letraAño FROM vista1");
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

	public ArrayList<Alumno> getAlumnos(String condicion){
		final String CONSULTA = "SELECT idProfesor,nombre,apellido1, apellido2 , fechaNacimiento,id,año,letraAño "
				+ "FROM vista2  "
				+ "WHERE " + condicion ;
		ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
		try {
			connection = DriverManager.getConnection(this.databaseConnection.getConnectionString());
			PreparedStatement ps = connection.prepareStatement(CONSULTA);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				alumnos.add(this.instanciarAlumno(rs));
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return alumnos;
	}

	public ArrayList<Alumno> getAlumnos(Curso curso){
		ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
		String sqlQuery = "SELECT numMatricula,nombre,apellido1, apellido2 , fechaNacimiento,id,año,letraAño "
				+ "FROM vista1  "
				+ "WHERE " +
				"letraAño='" + curso.getLetraAño() + "' AND año=" + curso.getAño();
		try {
			connection = DriverManager.getConnection(this.databaseConnection.getConnectionString());
			PreparedStatement ps = connection.prepareStatement(sqlQuery);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				alumnos.add(this.instanciarAlumno(rs, curso));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alumnos;
	}

	public Alumno getAlumnoByMatricula(int id){
		Alumno alumno = null;
		String sqlQuery = "SELECT numMatricula,nombre,apellido1, apellido2 , fechaNacimientoid,id,año,letraAño "
				+ "FROM vista1  "
				+ "WHERE numMatricula=?";
		try {
			connection = DriverManager.getConnection(this.databaseConnection.getConnectionString());
			PreparedStatement ps = connection.prepareStatement(sqlQuery);
			ps.setInt(1,id);
			ResultSet rs = ps.executeQuery();
			alumno = this.instanciarAlumno(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alumno;
	}
/**
 * 
 * @return
 */
	public ArrayList<Curso> getCursos(){
		ArrayList<Curso> cursos = new ArrayList<Curso>();
		try {
			connection = DriverManager.getConnection(this.databaseConnection.getConnectionString());
			PreparedStatement ps = connection.
					prepareStatement("SELECT id,año,letraAño FROM Curso");
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				cursos.add(this.instanciarCurso(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.databaseConnection.disconnect();
		}
		return cursos;
	}

	public ArrayList<Curso> getCursos(String condicion){
		final String CONSULTA = "SELECT id,año,letraAño "
				+ "FROM Curso WHERE año = " + condicion;
		ArrayList<Curso> cursos = new ArrayList<Curso>();
		try {
			connection = DriverManager.getConnection(this.databaseConnection.getConnectionString());
			PreparedStatement ps = connection.
					prepareStatement(CONSULTA);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				cursos.add(this.instanciarCurso(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.databaseConnection.disconnect();
		}
		return cursos;
	}

	public Curso getCursoById(int id){
		Curso curso = null;
		try {
			connection = DriverManager.getConnection(this.databaseConnection.getConnectionString());
			PreparedStatement ps = connection.
					prepareStatement("SELECT id,año,letraAño "
							+ "FROM Curso "
							+ "WHERE id =?");
			ps.setInt(1,id);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				curso = this.instanciarCurso(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.databaseConnection.disconnect();
		}
		return curso;
	}

	public ArrayList<Profesor> getProfesores(){
		ArrayList<Profesor> profesores = new ArrayList<Profesor>();
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
	public ArrayList<Profesor> getProfesores(String condicion){
		final String CONSULTA = "SELECT idProfesor,nombre,apellido1, apellido2 , fechaNacimiento,id,año,letraAño "
				+ "FROM vista2  "
				+ "WHERE " + condicion;
		ArrayList<Profesor> profesores = new ArrayList<Profesor>();
		try {
			connection = DriverManager.getConnection(this.databaseConnection.getConnectionString());
			PreparedStatement ps = connection.prepareStatement(CONSULTA);
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
	public  ArrayList<Profesor> getProfesores(Curso curso){
		ArrayList<Profesor> profesor = new ArrayList<Profesor>();
		String sqlQuery = "SELECT idProfesor,nombre,apellido1, apellido2 , fechaNacimiento,id,año,letraAño "
				+ "FROM vista2 WHERE letraAño='"
				+ curso.getLetraAño() + "' AND año=" + curso.getAño();
		try {
			connection = DriverManager.getConnection(this.databaseConnection.getConnectionString());
			PreparedStatement ps = connection.prepareStatement(sqlQuery);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				profesor.add(this.instanciarProfesor(rs, curso));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return profesor;
	}

	public Profesor getProfesorById(int id){
		Profesor profesor = null;
		try {
			connection = DriverManager.getConnection(this.databaseConnection.getConnectionString());
			PreparedStatement ps = connection.
					prepareStatement("SELECT idProfesor,nombre,apellido1, apellido2 , fechaNacimiento,id,año,letraAño "
							+ "FROM vista2  "
							+ "WHERE idProfesor=?");
			ps.setInt(1,id);
			ResultSet rs = ps.executeQuery();
			profesor = this.instanciarProfesor(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.databaseConnection.disconnect();
		}
		return profesor;
	}

	/**
	 * Actualiza el campo dado de todos los registros que cumplen con la condicion dada al valor dado dentro de la tabla dada
	 * @param nombreTabla
	 * @param nombreCampo
	 * @param condicion
	 * @param valor
	 */
	public void updateTabla(String nombreTabla,String nombreCampo, String condicion, String valor){
		final String CONSULTA = "UPDATE " + nombreTabla + " SET " + nombreCampo + " = '" + valor + "' WHERE " + condicion;
		this.ejecutarConsulta(CONSULTA);
	}
	/**
	 * Metodos para insertar datos en las tablas
	 */

	public void insertAlumno(Alumno alumno){
		final String CONSULTA = "INSERT INTO Alumno VALUES(" + "'" + alumno.getNumMatricula() + "'" + ","
				+ "'" + alumno.getNombre() + "'" + ","
				+ "'" + alumno.getApellido1() + "'" + ","
				+ "'" + alumno.getApellido2() + "'" + ","
				+ "'" + alumno.getCurso().getId() + "'" + ","
				+ "'" + alumno.getFechaNacimiento() + "'" + ")";
		this.ejecutarConsulta(CONSULTA);
	}
	public void insertAlumno(ArrayList<Alumno> alumnos){
		for (Alumno alumno:
			 alumnos) {
			this.insertAlumno(alumno);
		}
	}
	public void insertProfesor(Profesor profesor){
		final String CONSULTA = "INSERT INTO Profesor VALUES(" + "'" + profesor.getId() + "'" + ","
				+ "'" + profesor.getNombre() + "'" + ","
				+ "'" + profesor.getApellido1() + "'" + ","
				+ "'" + profesor.getApellido2() + "'" + ","
				+ "'" + profesor.getFechaNacimiento() + "'" + ","
				+ "'" + profesor.getCurso().getId() + "'" + ")";
		this.ejecutarConsulta(CONSULTA);
	}
	public void insertProfesor(ArrayList<Profesor> profesores) {
		for (Profesor profe :
				profesores) {
			this.insertProfesor(profe);
		}
	}
	public void insertCurso(Curso curso){
		final String CONSULTA = "INSERT INTO Curso VALUES("
				+ "'" + curso.getId() + "'" + ","
				+ "'" + curso.getAño() + "'" + ","
				+ "'" + curso.getLetraAño() + "'"
				+ ")";
		this.ejecutarConsulta(CONSULTA);
	}
	public void insertCurso(ArrayList<Curso> cursos){
		for (Curso curso :
				cursos) {
			this.insertCurso(curso);
		}
	}
	/**
	 * Metodos para borrar datos de las tablas
	 */
	public void deleteAlumno(int numMatricula){
		final String CONSULTA = "DELETE FROM Alumno WHERE numMatricula=" +numMatricula;
		this.ejecutarConsulta(CONSULTA);
	}
	public void deleteAlumno(String condicion){
		final String CONSULTA = "DELETE FROM Alumno WHERE " + condicion;
		this.ejecutarConsulta(CONSULTA);
	}
	public  void deleteAlumno(ArrayList<Alumno> alumnos){
		for (Alumno alumno:
			 alumnos){
			this.deleteAlumno(alumno.getNumMatricula());
		}
	}
	public void deleteProfesor(int id){
		final String CONSULTA = "DELETE FROM Profesor WHERE idProfesor=" +id;
		this.ejecutarConsulta(CONSULTA);
	}
	public void deleteProfesor(String condicion){
		final String CONSULTA = "DELETE FROM Profesor WHERE " + condicion;
		this.ejecutarConsulta(CONSULTA);
	}
	public  void deleteProfesor(ArrayList<Profesor> profesores){
		for (Profesor profesor:
				profesores){
			this.deleteProfesor(profesor.getId());
		}
	}
	public void deleteCurso(int id){
		final String CONSULTA = "DELETE FROM Curso WHERE id=" +id;
		this.ejecutarConsulta(CONSULTA);
	}
	public void deleteCurso(String condicion){
		final String CONSULTA = "DELETE FROM Curso WHERE " + condicion;
		this.ejecutarConsulta(CONSULTA);
	}
	public void deleteCurso(ArrayList<Curso> cursos){
		for (Curso curso:
			 cursos) {
			this.deleteCurso(curso.getId());
		}
	}
	/**
	 * Metodos que exportan la BD a formato xml
	 */
	public void exportarAlumnos(String ruta){
		Document documento = null;
		DocumentBuilder db = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			documento = db.newDocument();
			Element raiz = documento.createElement("alumnos");
			documento.appendChild(raiz);
			Element element = null;
			for (Alumno alumno:
				 this.getAlumnos()) {
				element = documento.createElement("alumno");
				element.setAttribute(NUM_MATRICULA, String.valueOf(alumno.getNumMatricula()));
				element.setAttribute(NOMBRE, alumno.getNombre());
				element.setAttribute(APELLIDO_1, alumno.getApellido1());
				element.setAttribute(APELLIDO_2, alumno.getApellido2());
				element.setAttribute(CURSO, String.valueOf(alumno.getCurso().getId()));
				element.setAttribute(FECHA_NACIMIENTO, String.valueOf(alumno.getFechaNacimiento()));
				raiz.appendChild(element);
			}
				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer optimusPrime = tf.newTransformer();
				DOMSource ds = new DOMSource(documento);
				StreamResult sr = new StreamResult(new File(ruta));
				optimusPrime.transform(ds, sr);

		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		} catch (TransformerException e) {
			throw new RuntimeException(e);
		}
	}
	public void exportarProfesores(String ruta){
		Document documento = null;
		DocumentBuilder db = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			documento = db.newDocument();
			Element raiz = documento.createElement("profesores");
			documento.appendChild(raiz);
			Element element = null;
			for (Profesor profesor:
					this.getProfesores()) {
				element = documento.createElement("profesor");
				element.setAttribute(ID_PROFESOR, String.valueOf(profesor.getId()));
				element.setAttribute(NOMBRE, profesor.getNombre());
				element.setAttribute(APELLIDO_1, profesor.getApellido1());
				element.setAttribute(APELLIDO_2, profesor.getApellido2());
				element.setAttribute(CURSO, String.valueOf(profesor.getCurso().getId()));
				element.setAttribute(FECHA_NACIMIENTO, String.valueOf(profesor.getFechaNacimiento()));
				raiz.appendChild(element);
			}
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer optimusPrime = tf.newTransformer();
			DOMSource ds = new DOMSource(documento);
			StreamResult sr = new StreamResult(new File(ruta));
			optimusPrime.transform(ds, sr);

		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		} catch (TransformerException e) {
			throw new RuntimeException(e);
		}
	}
	public void exportarCursos(String ruta){
		Document documento = null;
		DocumentBuilder db = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			documento = db.newDocument();
			Element raiz = documento.createElement("cursos");
			documento.appendChild(raiz);
			Element element = null;
			for (Curso curso:
					this.getCursos()) {
				element = documento.createElement("curso");
				element.setAttribute(ID, String.valueOf(curso.getId()));
				element.setAttribute(AÑO, String.valueOf(curso.getAño()));
				element.setAttribute(LETRA_AÑO, curso.getLetraAño());
				raiz.appendChild(element);
			}
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer optimusPrime = tf.newTransformer();
			DOMSource ds = new DOMSource(documento);
			StreamResult sr = new StreamResult(new File(ruta));
			optimusPrime.transform(ds, sr);
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		} catch (TransformerException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * Metodos para importar datos de un documento XML a objetos java
	 */
	public ArrayList<Alumno> importAlumnoXml(String ruta){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
		Curso curso = null;
		try {
			db = dbf.newDocumentBuilder();
			Document documento = db.parse(new File(ruta));
			NodeList nl = documento.getElementsByTagName("alumno");
			for (int i=0;i < nl.getLength();i++){
				NamedNodeMap nnm = nl.item(i).getAttributes();
				curso = this.getCursoById(Integer.valueOf(nnm.getNamedItem(CURSO).getNodeValue()));
				alumnos.add(new Alumno(
						Integer.valueOf(nnm.getNamedItem(NUM_MATRICULA).getNodeValue()),
						nnm.getNamedItem(NOMBRE).getNodeValue(),
						nnm.getNamedItem(APELLIDO_1).getNodeValue(),
						nnm.getNamedItem(APELLIDO_2).getNodeValue(),
						curso,
						Date.valueOf(nnm.getNamedItem(FECHA_NACIMIENTO).getNodeValue())
						)
				);
			}

		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		}
		return alumnos;
	}
	public ArrayList<Profesor> importProfesorXml(String ruta){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		ArrayList<Profesor> profesores = new ArrayList<Profesor>();
		Curso curso = null;
		try {
			db = dbf.newDocumentBuilder();
			Document documento = db.parse(new File(ruta));
			NodeList nl = documento.getElementsByTagName("profesor");
			for (int i=0;i < nl.getLength();i++){
				NamedNodeMap nnm = nl.item(i).getAttributes();
				curso = this.getCursoById(Integer.valueOf(nnm.getNamedItem(CURSO).getNodeValue()));
				profesores.add(new Profesor(
								Integer.valueOf(nnm.getNamedItem(ID_PROFESOR).getNodeValue()),
								nnm.getNamedItem(NOMBRE).getNodeValue(),
								nnm.getNamedItem(APELLIDO_1).getNodeValue(),
								nnm.getNamedItem(APELLIDO_2).getNodeValue(),
								Date.valueOf(nnm.getNamedItem(FECHA_NACIMIENTO).getNodeValue()),
								curso));
			}

		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		}
		return profesores;
	}
	public ArrayList<Curso> importCursosXml(String ruta){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		ArrayList<Curso> cursos = new ArrayList<Curso>();
		Curso curso = null;
		try {
			db = dbf.newDocumentBuilder();
			Document documento = db.parse(new File(ruta));
			NodeList nl = documento.getElementsByTagName("curso");
			for (int i=0;i < nl.getLength();i++){
				NamedNodeMap nnm = nl.item(i).getAttributes();
				curso = new Curso(
						Integer.valueOf(nnm.getNamedItem(ID).getNodeValue()),
						Integer.valueOf(nnm.getNamedItem(AÑO).getNodeValue()),
						nnm.getNamedItem(LETRA_AÑO).getNodeValue());
				curso.setAlumnos(this.getAlumnos(curso));
				curso.setProfesores(this.getProfesores(curso));
				cursos.add(curso);
			}

		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		}
		return cursos;
	}
	/**
	 * Metodos helper que realizan ciertas acciones que se repiten varias veces a lo largo del codigo
	 */
	private Alumno instanciarAlumno(ResultSet rs) {
		Alumno alumno = null;
		try {
			rs.setFetchSize(8);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
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
			if (rs.getFetchSize() == 0)
				rs.setFetchSize(3);
			curso = new Curso(
					rs.getInt(rs.getFetchSize()-2),
					rs.getInt(rs.getFetchSize()-1),
					rs.getString(rs.getFetchSize()));
			ArrayList<Alumno> alumnos = this.getAlumnos(curso);
			curso.setAlumnos(alumnos);
			ArrayList<Profesor> profesores = this.getProfesores(curso);
			curso.setProfesores(profesores);


		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return curso;
	}
	private Profesor instanciarProfesor(ResultSet rs) {
		Profesor profesor = null;
		try {
			rs.setFetchSize(8);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
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

	/**
	 * Este metodo abre y cierra un conexion para ejecutar la consulta dada
	 * @param consulta
	 */
	private void ejecutarConsulta(String consulta){
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(this.databaseConnection.getConnectionString());
			ps = connection.prepareStatement(consulta);
			ps.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			this.databaseConnection.disconnect();
		}

	}
}