package proyecto_final;

import java.sql.*;

public class SchemaManager {
    DatabaseConnection databaseConnection;

    public SchemaManager(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void createDatabase(){
        try {
            Connection connection = DriverManager.getConnection(databaseConnection.getConnectionString());
            PreparedStatement ps = connection.
                    prepareStatement( "DROP DATABASE instituto");
            ps.execute();
            ps = connection.
                    prepareStatement( "CREATE DATABASE instituto");
            ps.execute();
            ps = connection.
                    prepareStatement( "USE instituto;");
            ps.execute();
            ps = connection.
                    prepareStatement( "CREATE TABLE Alumno(numMatricula int PRIMARY KEY,nombre varchar(20),apellido1 varchar(20),apellido2 varchar(20),idCurso int, fechaNacimiento DATE);");
            ps.execute();
            ps = connection.
                    prepareStatement( "CREATE TABLE Curso(id int PRIMARY key,año int,letraAño character);");
            ps.execute();
            ps = connection.
                    prepareStatement("CREATE TABLE Profesor(idProfesor int PRIMARY key, nombre varchar(20), apellido1 varchar(20), apellido2 varchar(20), fechaNacimiento DATE, idCurso int);");
            ps.execute();
            ps = connection.
                    prepareStatement("ALTER TABLE Alumno add CONSTRAINT fk FOREIGN KEY (idCurso) REFERENCES Curso(id) ON DELETE CASCADE ON UPDATE CASCADE;");
            ps.execute();
            ps = connection.
                    prepareStatement("ALTER TABLE Profesor add CONSTRAINT fk2 FOREIGN KEY (idCurso) REFERENCES Curso(id) ON DELETE CASCADE ON UPDATE CASCADE;");
            ps.execute();
            ps = connection.
                    prepareStatement("CREATE VIEW vista2 AS SELECT Profesor.*, Curso.* FROM Profesor join Curso on Profesor.idCurso = Curso.id;");
            ps.execute();
            ps = connection.
                    prepareStatement("CREATE VIEW vista1 AS SELECT Alumno.*,Curso.* FROM Alumno join Curso on Alumno.idCurso = Curso.id ;");
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            databaseConnection.disconnect();
        }
    }
    public void addSampleData(){
        try {
            Connection connection = DriverManager.getConnection(databaseConnection.getConnectionString());
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Curso VALUES (1,1,'A');");
            ps.execute();
            ps = connection.prepareStatement("INSERT INTO Profesor VALUES (1, 'Fran','Mendez', 'Lopez','1998-09-01',1);");
            ps.execute();
            ps = connection.prepareStatement("INSERT INTO Alumno VALUES (1,'Paco','Chocolatero','Franco',1,'2004-04-05');");
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            databaseConnection.disconnect();
        }

    }
}
