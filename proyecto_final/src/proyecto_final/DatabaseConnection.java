package proyecto_final;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
	private Connection connection;
	private String connectionString;
	
	public DatabaseConnection(@NotNull String connectionString) {
		//guarda los datos de conexión
		this.connectionString = connectionString;
		try {
			//registrar el controlador
			DriverManager.registerDriver (new com.mysql.cj.jdbc.Driver());
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	/**
	 * Guarda el objeto de conexión
	 * @param connection
	 * @return Si es distinto de null devuelve true
	 */
	public boolean connect(Connection connection) {		
		this.connection = connection;
		return this.connection==null?false:true;
	}
	public boolean disconnect() {
		try {
			if(this.connection==null) return true;
			this.connection.close();
			this.connectionString = "";
			return true;
		} catch (SQLException e) {			
			return false;
		}
	}
	public Connection getConnection() {
		return this.connection;
	}
	public boolean isConnected() {
		try {
			return !this.connection.isClosed();
		} catch (SQLException e) {			
			e.printStackTrace();
			return false;
		}
	}
	public String getConnectionString() {
		return this.connectionString;
	}
}
