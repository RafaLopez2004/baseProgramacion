# Libreria base de datos

## Descripcion general

Libreria que provee las clases y metodos para la sencilla gestion de la BD instituto, para ello usamos principalmente 3 clases:

- DatabaseConnection: Crear, mantiene y cierra la conexión con la base de datos, debemos pasarle un string en su constructor que corresponde con la conexión y sus credenciales, la conexion debe apuntar a una BD existente, lo cual debera poseer un formato similar a este:
```
new DatabaseConection(jdbc:mysql://localhost/instituto?user=USUARIOBD&password=CONTRASEÑABD");
```
En caso de que su usuario no tenga contraseña no ponga nada despues del =.

- SchemaManager: Nos permite crear una BD que cumple con las asunciones de los metodos que la gestionan, ademas de poder introducir datos de ejemplo para el testeo.

- DatabaseManager:Posee todos los metodos que gestionan la base de datos.

## Funcionalidades
Esta libreria permite:

- La extraccion de datos de la base y su conversion a objetos java, a traves de multiples filtros.

- La creacion uno o varios registros en cualquiera de las tablas a traves del metodo insertAlumno/Profesor/Curso pasandole un objeto de la respectiva clase.

- La eliminacion de uno o varios registros a traves de condiciones, o a traves del respectivo objeto o array de objetos.

- La actualizacion de uno o varios registros de cualquier tabla, a traves del nombre de la tabla y del campo, una condicion y el nuevo valor.

- Exportacion y importacion de da la BD a formato XML.

# Dependencias
- mysql-connector, esta es la libreria que permite la conexión con nuestra base de datos.

- org.eclipse.jdt.annotation, usaremos la anotacion nonNull para evitar que se nos pasen referencias nulas.

# Especificaciones de uso

## Creacion de la base de datos
Esta libreria trabaja sobre un esquema concreto, por lo es recomendable que la BD sea creada a traves del metodo createDatabase de la clase SchemaManager, ya que los metodos de las clase gestora 
tienen asumen que las tablas, campos y vistas se llaman exactamente igual a como se nombran con dicho metodo.

## Uso de fechas
Esta libreria usa las java.sql.date a la hora de usar fechas
