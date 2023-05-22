package proyecto_final;

import java.util.ArrayList;
import java.sql.Date;

public class PruebaBase {
    public static void main(String[] args) {
        SchemaManager sm = new SchemaManager(new DatabaseConnection("jdbc:mysql://localhost/instituto?user=root&password="));
        sm.createDatabase();
        sm.addSampleData();
        DatabaseManager databaseManager = new DatabaseManager(new DatabaseConnection("jdbc:mysql://localhost/instituto?user=root&password="));

        System.out.println("Pruebas de insercion, actualizacion y eliminado de datos");
        ArrayList<Curso> cursos = new ArrayList<Curso>();
        cursos.add(new Curso(2,2,"A"));
        cursos.add(new Curso(3,1,"B"));
        databaseManager.deleteCurso(cursos);
        databaseManager.insertCurso(cursos);

        cursos = new ArrayList<Curso>(databaseManager.getCursos());
        for (Curso curso : cursos){
            System.out.println(curso);
        }

        databaseManager.updateTabla("Alumno","nombre", "nombre='Paco'", "Paquita");
        ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
        alumnos.add(new Alumno(2,"Maria", "Carmen", "Felix", cursos.get(1), Date.valueOf("2004-05-04")));
        alumnos.add(new Alumno(3,"Fulanito", "ejemplo", "ejemplo2", cursos.get(2), Date.valueOf("2003-10-09")));
        alumnos.add(new Alumno(4,"Rafa", "Lopez", "Felix", cursos.get(1), Date.valueOf("2004-05-30")));

        databaseManager.deleteAlumno(alumnos);
        databaseManager.insertAlumno(alumnos);

        alumnos = new ArrayList<Alumno>(databaseManager.getAlumnos());
        for (Alumno alumno:
             alumnos) {
            System.out.println(alumno);
        }

        databaseManager.updateTabla("Profesor","apellido1", "idProfesor=1", "aaaaaaaaaaaaa");

        ArrayList<Profesor> profesores = new ArrayList<Profesor>();
        profesores.add(new Profesor(2,"Pepe","bbbb","ccccc", Date.valueOf("1989-06-20"), cursos.get(1)));
        profesores.add(new Profesor(3,"Marta","ejemplo","Venganito", Date.valueOf("1980-04-05"),  cursos.get(2)));

        databaseManager.deleteProfesor(profesores);
        databaseManager.insertProfesor(profesores);
        profesores = new ArrayList<Profesor>(databaseManager.getProfesores());
        for (Profesor profesor:profesores){
            System.out.println(profesor);
        }
        System.out.println("Comprobacion de que se han añadido bien los alumnos a su correspondiente curso");
        cursos = new ArrayList<Curso>(databaseManager.getCursos());
        for (Curso curso : cursos){
            System.out.println(curso);
        }

        System.out.println("Pruebas XML");
        System.out.println("Alumnos");
        databaseManager.exportarAlumnos("aquiMismo.xml");
        alumnos = databaseManager.importAlumnoXml("aquiMismo.xml");
        for (Alumno alumno:
                alumnos)
            System.out.println(alumno);
        System.out.println("Profesores");
        databaseManager.exportarProfesores("alliMismo.xml");
        profesores = databaseManager.importProfesorXml("alliMismo.xml");
        for (Profesor profesor:
             profesores)
            System.out.println(profesor);
        System.out.println("Cursos");
        databaseManager.exportarCursos("enEseSitio.xml");
        cursos = databaseManager.importCursosXml("enEseSitio.xml");
        for (Curso curso:
                cursos)
            System.out.println(curso);

        }

    }
