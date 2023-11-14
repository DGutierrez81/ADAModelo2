package Controlador
import Vista.*
import Modelo.*
import java.io.File
import java.io.FileWriter
import java.io.IOException

class Controlador(private val vista: Vista, private val gestor: GestorBBDD) {

    /**
     * Realiza todas conexiones entre el GestorBBDD y la vista
     */
    fun Iniciar(){
        vista.ConectarBBDD(gestor.ConectarBBDD())
        gestor.LogModeloM(gestor.ConectarBBDD())
        var estado = true
        while(estado){
            when(vista.inicio()){
                // Opción que manda la desconexión de la BBDD.
                0 -> {
                    vista.salir(gestor.desconectarBBDD())
                    LogModeloC("\nSe ha pulsado el 0, desconexión del programa" + gestor.desconectarBBDD())
                    estado = false
                }
                1 -> {
                    // Opción que permite la isercción de los datos.
                    val producto = vista.IngresarDatos()
                    val mensaje = gestor.Insertar(producto.id, producto.nombre, producto.precio)
                    vista.ConsultarDatoEspecifico(mensaje)
                    gestor.LogModeloM(mensaje + "\n${producto.id}, ${producto.nombre}, ${producto.precio}")
                    LogModeloC("\nSe ha pulsado el 1, insercción de los datos")
                }
                // Opción que permite la consulta global de los datos.
                2 -> {
                    vista.ConsultarDatos(gestor.SelecTodo())
                    gestor.LogModeloM(vista.ConsultarDatos(gestor.SelecTodo()))
                    LogModeloC("\nSe ha pulsado el 2, consultar todos los datos")
                }
                // Opción que permite realizar una consulta específica.
                3 -> {
                    val id = vista.ObtenerId()
                    val consulta = gestor.ConsultaEspecifica(id)
                    vista.ConsultarDatoEspecifico(consulta)
                    gestor.LogModeloM("Se ha solocitado el id: " + id)
                    LogModeloC("\nSe ha pulsado el 3, consulta específica")
                }
                // Opción modificar los datos.
                4 -> {
                    val producto = vista.IngresarDatos()
                    val mensaje = gestor.ModidificarDatos(producto.id, producto.nombre, producto.precio)
                    vista.ConsultarDatoEspecifico(mensaje)
                    gestor.LogModeloM(mensaje + "\n${producto.id}, ${producto.nombre}, ${producto.precio}")
                    LogModeloC("\nSe ha pulsado el 4, modificar datos")
                }
                // Opción que permite eliminar los datos.
                5 -> {
                    val id = vista.ObtenerId()
                    val mensaje = gestor.BorrarFila(id)
                    vista.ConsultarDatoEspecifico(mensaje)
                    gestor.LogModeloM(mensaje)
                    LogModeloC("\nSe ha pulsado el 5, eliminar datos")
                }
            }
        }
    }

    fun LogModeloC(mensaje: String){
        val nombreArchivo = "archivoControlador.log"

        val archivo = File(nombreArchivo)

        // Abre el archivo en modo de adición (append mode)
        val escritor = FileWriter(archivo, true)

        // Ahora puedes escribir contenido en el archivo sin sobrescribirlo
        val contenido = mensaje + "\n"
        escritor.write(contenido)

        escritor.close()

    }
}