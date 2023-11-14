package Controlador

import Modelo.GestorBBDD
import Vista.Vista
import guardarLog
import java.util.*

class Controlador(vista : Vista) {
    var vista = vista
    var gestor = GestorBBDD()
    var log = guardarLog("Controlador.log")

    /**
     * Controla las opciones de la vista y del gestor de la base de datos
     */
    fun comenzar(){
        try {
            gestor.conectarBBDD()
            var actual = true
            while (actual){
                when(vista.imprimirMenu()){
                    1-> {
                        val trabajadorAgregar = vista.pedirAgregarDatos()
                        vista.imprimir(gestor.agregarTrabajadorBaseDeDatos(trabajadorAgregar))
                        log.appendText("Opcion 1 ${Date()} -> Insertar\n-\n")
                    }
                    2-> {
                        val trabajadorActualizar = vista.pedirActualizarDatos()
                        vista.imprimir(gestor.actualizarTrabajador(trabajadorActualizar.first,trabajadorActualizar.second))
                        log.appendText("Opcion 2 ${Date()} -> Actualizar\n-\n")
                    }
                    3-> {
                        val trabajadorEliminar = vista.pedirDatosEliminar()
                        vista.imprimir(gestor.eliminarTrabajador(trabajadorEliminar))
                        log.appendText("Opcion 3 ${Date()} -> Eliminar\n-\n")
                    }
                    4-> {
                        val trabajadorBuscar = vista.pedirDatosBuscar()
                        vista.imprimir(gestor.recuperarTrabajador(trabajadorBuscar))
                        log.appendText("Opcion 4 ${Date()} -> Buscar\n-\n")
                    }
                    5-> {
                        vista.imprimir(gestor.recuperarTrabajadores())
                        log.appendText("Opcion 5 ${Date()} -> Buscar todos\n-\n")
                    }
                    6-> {
                        vista.salirMenu()
                        gestor.cerrarBBDD()
                        actual = false
                        log.appendText("Opcion 6 ${Date()} -> Salir\n-\n")
                    }
                    else-> throw Exception()

                }
            }
        }catch (e:Exception){
            vista.imprimir("Opción no disponible")
            log.appendText("ERROR ${Date()} -> Opción inválida ${vista.imprimirMenu()}\n-\n")
            comenzar()
        }
    }
}