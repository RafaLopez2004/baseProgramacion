# Libreria base de datos

Libreria usada para la gestion de la base de mi base de datos Colegio a traves de codigo java

# Especificaciones de uso

La base de datos sobre la que puede trabajar esta libreria debe ser creada a traves del metodo createDatabase de la clase SchemaManager, ya que los metodos de las clase gestora 
llama a vistas creadas dentro de dicho metodo, además SchemaManager posee un metodo que introducira datos de ejemplo llamado addSampleData.

Las clase SchemaManager y DatabaseManager requieren de un objeto de tipo DatabaseConection para ser construidos, la cual requiere un String que contenga la informacion necesaria
para realizar correctamente la conexión, dicha informacion debe tener el siguiente formato: 

```
new DatabaseConection(jdbc:mysql://localhost/instituto?user=USUARIOBD&password=CONTRASEÑABD");
```
En caso de que su usuario no tenga contraseña no ponga nada despues del =.

