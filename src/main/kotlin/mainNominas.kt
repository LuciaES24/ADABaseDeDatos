import Controlador.Controlador
import Vista.Vista

fun main(){
    val vista = Vista()
    val controlador = Controlador(vista)

    controlador.comenzar()
}