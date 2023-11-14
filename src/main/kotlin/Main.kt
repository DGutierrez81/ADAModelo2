import Modelo.*
import Vista.*
import Controlador.*


fun main(args: Array<String>) {
    val gestor = GestorBBDD()
    val vista = Vista()
    val controlador = Controlador(vista, gestor)

    controlador.Iniciar()
}