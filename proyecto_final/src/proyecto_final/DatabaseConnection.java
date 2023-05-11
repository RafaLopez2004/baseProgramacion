package proyecto_final;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.eclipse.jdt.annotation.NonNull;




public class DatabaseConnection {
	private Connection connection;
	//"jdbc:mysql://localhost/biblioteca?user=root&password=root"
	public boolean connect(@NonNull String connectionString) {		
		try {
			//cargar el driver
			DriverManager.registerDriver (new com.mysql.cj.jdbc.Driver());


			//crear un objeto de conexión
			this.connection = 
					DriverManager.getConnection(connectionString);			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return connection==null?false:true;
	}
	public boolean disconnect() {
		try {
			this.connection.close();
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
}
