package Modelo

import java.io.*
import java.lang.Exception
import java.nio.channels.SelectableChannel
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement

class GestorBBDD {
    private var url: String? = null
    private var username: String? = null
    private var password: String? = null
    private var connection: Connection? = null
    private var statement: Statement? = null

    /**
     * Se realiza la conexión a la BBDD
     * @return Devuelve un mensaje de confirmación o de error.
     */

    fun ConectarBBDD(): String{
        return try{
            Class.forName("oracle.jdbc.driver.OracleDriver")
            url = "jdbc:oracle:thin:@localhost:1521:XE"
            username = "actividad"
            password = "actividad"
            connection = DriverManager.getConnection(url, username, password)
            "Conexión realizada a la BBDD con éxito.\nBienvenido a la BBDD.\n   \n    "

        }catch (e: SQLException) {
            "Error en la conexión: ${e.message}"
        } catch (e: ClassNotFoundException) {
            "No se encontró el driver JDBC: ${e.message}"
        }
    }

    /**
     * Se crea la tabla aunque no está incluida en el modelo.
     */
    fun CrearTabla(){
        try{

            val statemnt2 = connection!!.createStatement()
            val crearTabla = Sentencia.crearTabla


            statemnt2!!.execute(crearTabla)

        } catch (e: SQLException) {
            println("Error en la conexión: ${e.message}")
        } catch (e: ClassNotFoundException) {
            println("No se encontró el driver JDBC: ${e.message}")
        }
        catch (e: NullPointerException) {
            println("No se inició la BBDD: ${e.message}")
        }
    }

    /**
     * Modifica los datos de la tabla
     * @param id identificador de cada producto
     * @param nombre es el nombre del producto
     * @param precio es el precio del producto
     */

    fun ModidificarDatos(id: Int, nombre: String, precio: Float): String{
        var actualizar = 0
        return try{
            val producto = connection!!.prepareStatement(Sentencia.modficaNombre)
            producto.setString(1, nombre)
            producto.setFloat(2, precio)
            producto.setInt(3, id)

            actualizar = producto.executeUpdate()

            if (actualizar > 0) {
                "El registro ha sido actualizado exitosamente."
            } else {
                "No se ha actualizado ningún registro."
            }
        } catch (e: SQLException) {
            "Error en la conexión: ${e.message}"
        } catch (e: ClassNotFoundException) {
            "No se encontró el driver JDBC: ${e.message}"
        }
    }

    /**
     * Se le pasa identificador para borrar el producto deseado
     * @param id identificador del producto.
     */
    fun BorrarFila(id: String): String{
        return try{
            val producto = connection!!.prepareStatement(Sentencia.borrarFila)
            producto.setString(1, id)

            val columna = producto.executeUpdate()

            if(columna > 0){
                "Registro borrado correctamente."
            }else{
                "No existe el registro."
            }

        }catch (e: SQLException) {
            "Error en la conexión: ${e.message}"
        }
    }

    /**
     * Pasando los datos introduce un nuevo producto en la tabla.
     * @param id identificador de cada producto
     * @param nombre es el nombre del producto
     * @param precio es el precio del producto
     */
    fun Insertar(id: Int, nombre: String, precio: Float): String{
        return try{
            val insertar = connection!!.prepareStatement(Sentencia.insertar)
            insertar.setInt(1, id)
            insertar.setString(2, nombre)
            insertar.setFloat(3, precio)
            insertar.executeUpdate()
            "Registro añadido correctamente."
        }catch (e: SQLException) {
            "Error al ingresar el registro: ${e.message}"
        }
    }

    /**
     * Permite realizar una consulta global de la tabla
     * @return Devuelve una lista del objeto producto
     */
    fun SelecTodo(): MutableList<Producto>?{
        return try{
            val resultado = mutableListOf<Producto>()
            statement = connection!!.createStatement()
            val consulta = statement!!.executeQuery(Sentencia.selectTodo)
            val result = mutableListOf<Map<String, Any>>()
            while(consulta.next()){
                val id = consulta.getInt("Id")
                val nombre = consulta.getString("Nombre")
                val precio = consulta.getFloat("Precio")
                resultado.add(Producto(id, nombre, precio))

            }
            if(resultado.isEmpty()) throw Exception()  else resultado
        }catch (e: Exception) {
            null
        }
    }

    /**
     * Permite realizar una consulta de un producto en concreto a través de su identificador
     * @param id identificador del producto
     * @return devuelve un String con la fila del producto deseado
     */
    fun ConsultaEspecifica(id: String): String{
        return try{
            var resultado = ""

            val consulta = connection!!.prepareStatement(Sentencia.selectDato)
            consulta.setString(1, id)
            val state = consulta.executeQuery()
            while(state.next()){
                val _id = state.getInt("Id")
                val nombre = state.getString("Nombre")
                val precio = state.getFloat("Precio")
                val producto = Producto(_id, nombre, precio)
                resultado = producto.toString()
            }
            if(resultado.isEmpty()) throw Exception()  else resultado
        }catch (e: Exception) {
            return "No se encuentra en la tabla"
        }
    }

    /**
     * Realiza la desconexión de la BBDD
     * @return Devuleve un mensaje de confirmacion
     */
    fun desconectarBBDD(): String{
        var resultado = ""

        connection!!.close()
        resultado = "Desconectado correctamente de la BBDD."

        return resultado
    }

    fun LogModeloM(mensaje: String){
        val nombreArchivo = "archivoGestor.log"

        val archivo = File(nombreArchivo)

        // Abre el archivo en modo de adición (append mode)
        val escritor = FileWriter(archivo, true)

        // Ahora puedes escribir contenido en el archivo sin sobrescribirlo
        val contenido = mensaje + "\n"
        escritor.write(contenido)

        escritor.close()
    }
}
