DROP DATABASE IF EXISTS instituto;
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

-- ALTER TABLE Alumno 
-- 	add CONSTRAINT fk FOREIGN KEY (idCurso) REFERENCES Curso(id);

INSERT INTO Alumno
	VALUES (1,"Paco","Chocolatero","Franco",1,"2004-04-05");

INSERT INTO Curso
	VALUES (1,1,"A",1);

CREATE VIEW a AS 
	SELECT Alumno.*,
	Curso.*
	FROM Alumno join Curso on Alumno.idCurso = Curso.id ;
    
Select * from a;
