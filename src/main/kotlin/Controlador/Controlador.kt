package Controlador

import Modelo.GestorBBDD
import Vista.Vista

class Controlador(vista : Vista) {
    var vista = vista
    var gestor = GestorBBDD()

    fun comenzar(){
        gestor.conectarBBDD()
        var actual = true
        while (actual){
            when(vista.imprimirMenu()){
                1-> {
                    var trabajadorAgregar = vista.pedirAgregarDatos()
                    gestor.agregarTrabajadorBaseDeDatos(gestor.conn!!,trabajadorAgregar)
                }
                2-> {
                    var trabajadorActualizar = vista.pedirActualizarDatos()
                    gestor.actualizarTrabajador(gestor.conn!!,trabajadorActualizar.first,trabajadorActualizar.second)
                }
                3-> {
                    var trabajadorEliminar = vista.pedirDatosEliminar()
                    gestor.eliminarTrabajador(gestor.conn!!,trabajadorEliminar)
                }
                4-> {
                    var trabajadorBuscar = vista.pedirDatosBuscar()
                    println(gestor.recuperarTrabajador(gestor.conn!!,trabajadorBuscar))
                }
                5-> {
                    println(gestor.recuperarTrabajadores(gestor.conn!!))
                }
                6-> {
                    vista.salirMenu()
                    gestor.cerrarBBDD(gestor.conn!!)
                    actual = false
                }
            }
        }
    }
}