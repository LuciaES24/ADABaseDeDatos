package Controlador

import Modelo.GestorBBDD
import Vista.Vista

class Controlador(vista : Vista) {
    var vista = vista
    var gestor = GestorBBDD()

    /**
     * Controla las opciones de la vista y del gestor de la base de datos
     */
    fun comenzar(){
        gestor.conectarBBDD()
        var actual = true
        while (actual){
            when(vista.imprimirMenu()){
                1-> {
                    var trabajadorAgregar = vista.pedirAgregarDatos()
                    gestor.agregarTrabajadorBaseDeDatos(trabajadorAgregar)
                }
                2-> {
                    var trabajadorActualizar = vista.pedirActualizarDatos()
                    gestor.actualizarTrabajador(trabajadorActualizar.first,trabajadorActualizar.second)
                }
                3-> {
                    var trabajadorEliminar = vista.pedirDatosEliminar()
                    gestor.eliminarTrabajador(trabajadorEliminar)
                }
                4-> {
                    var trabajadorBuscar = vista.pedirDatosBuscar()
                    vista.imprimirTrabajador(gestor.recuperarTrabajador(trabajadorBuscar))
                }
                5-> {
                    vista.imprimirTrabajador(gestor.recuperarTrabajadores())
                }
                6-> {
                    vista.salirMenu()
                    gestor.cerrarBBDD()
                    actual = false
                }
            }
        }
    }
}