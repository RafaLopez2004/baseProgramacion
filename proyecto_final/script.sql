DROP DATABASE instituto;
CREATE DATABASE instituto;
USE instituto;

CREATE TABLE Alumno(
	 numMatricula int PRIMARY KEY,
	 nombre varchar(20),
	 apellido1 varchar(20),
	 apellido2 varchar(20),
	 idCurso int,
	 fechaNacimiento DATE
);

CREATE TABLE Curso(
	id int PRIMARY key,
    año int,
    letraAño character
);

CREATE TABLE Profesor(
	idProfesor int PRIMARY key,
	nombre varchar(20),
	apellido1 varchar(20),
	apellido2 varchar(20),
	fechaNacimiento DATE,
	idCurso int
);
ALTER TABLE Alumno 
add CONSTRAINT fk FOREIGN KEY (idCurso) REFERENCES Curso(id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Profesor 
add CONSTRAINT fk2 FOREIGN KEY (idCurso) REFERENCES Curso(id) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE VIEW vista1 AS
	SELECT Alumno.*,
	Curso.*
	FROM Alumno join Curso on Alumno.idCurso = Curso.id ;

CREATE VIEW vista2 AS
	SELECT Profesor.*,
	Curso.*
	FROM Profesor join Curso on Profesor.idCurso = Curso.id;

INSERT INTO Curso
	VALUES (1,1,"A");

INSERT INTO Alumno
	VALUES (1,"Paco","Chocolatero","Franco",1,"2004-04-05");

INSERT INTO Profesor
	VALUES (1, "Fran","Mendez", "Lopez","1998-09-01",1);


