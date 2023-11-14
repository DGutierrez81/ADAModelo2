package Modelo

/**
 * Tiene todas las acciones a realizar de la tabla SQl.
 */
object Sentencia {
    val crearTabla = "CREATE TABLE Producto (Id     NUMBER(2), Nombre     VARCHAR2(30), Precio    DECIMAL(10,2), CONSTRAINT    PK_Producto   PRIMARY KEY(Id))"
    val modficaNombre = "UPDATE producto SET nombre = ?, precio = ?  WHERE Id = ?"
    val borrarFila = "DELETE FROM producto WHERE Id = ?"
    val insertar = "INSERT INTO producto(Id, Nombre, Precio) VALUES(?, ?, ?)"
    val selectTodo = "SELECT * FROM producto"
    val selectDato = "SELECT * FROM producto WHERE id = ?"
}