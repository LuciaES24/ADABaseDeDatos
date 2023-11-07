package Modelo

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class GestorBBDD {

    var conn : Connection? = null

    /**
     * Hace la conexión a la base de datos
     */
    fun conectarBBDD(){
        val url = "jdbc:oracle:thin:@localhost:1521:xe"
        val usuario = "ADA"
        val contrasena = "ADA"
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver")
            conn = DriverManager.getConnection(url, usuario, contrasena)
        }catch (e: SQLException) {
            println("Error en la conexión: ${e.message}")
        } catch (e: ClassNotFoundException) {
            println("No se encontró el driver JDBC: ${e.message}")
        }

    }

    /**
     * Cierra la conexión de la base de datos
     */
    fun cerrarBBDD(){
        conn!!.close()
    }

    /**
     * Añade un trabaador a la base de datos
     * @param trabajador objeto Trabajador con sus datos completos
     */
    fun agregarTrabajadorBaseDeDatos(trabajador: Trabajador){
        val stmt = conn!!.prepareStatement("INSERT INTO TRABAJADORES (DNI , NOMBRE , APELLIDOS , FECHA_NAC ) VALUES (?, ?, ?, ?)")
        stmt.setString(1, trabajador.dniTrabajador)
        stmt.setString(2,trabajador.nombreTrabajador)
        stmt.setString(3,trabajador.apellidosTrabajador)
        stmt.setString(4,trabajador.fecha_nacTrabajador)
        stmt.executeUpdate()
    }

    /**
     * Recupera un trabajador de la base de datos a partir de un DNI
     * @param dni dni del trabajador que queremos buscar
     * @return string con todos los datos del trabajador
     */
    fun recuperarTrabajador(dni:String) : String{
        try {
            val stmt = conn!!.prepareStatement("SELECT DNI , NOMBRE , APELLIDOS , FECHA_NAC FROM TRABAJADORES WHERE DNI = ?")
            stmt.setString(1,dni)
            val result = stmt.executeQuery()
            var busqueda = ""
            while (result.next()){
                val dniObtenido = result.getString("DNI")
                val nombreObtenido = result.getString("NOMBRE")
                val apellidosObtenidos = result.getString("APELLIDOS")
                val fechaObtenido = result.getString("FECHA_NAC")
                val trabajadorBuscado = Trabajador(dniObtenido,nombreObtenido,apellidosObtenidos,fechaObtenido)
                busqueda = trabajadorBuscado.toString()
            }
            return if (busqueda == "") throw Exception() else busqueda
        }catch (e:Exception){
            return "No se encontraron resultados"
        }
    }

    /**
     * Elimina un trabajador de la base de datos
     * @param dni string con el DNI del trabajador que queremos eliminar
     */
    fun eliminarTrabajador(dni: String){
        val stmt = conn!!.prepareStatement("DELETE FROM TRABAJADORES WHERE DNI = ?")
        stmt.setString(1, dni)
        stmt.executeUpdate()
    }

    /**
     * Actualiza los datos de un trabajador de la base de datos
     * @param dni string con el dni del trabajador al que queremos actualizarle los datos
     * @param trabajador objeto Trabajador con los nuevos datos
     * @return string con el resultado de la función
     */
    fun actualizarTrabajador(dni: String, trabajador: Trabajador):String{
        try {
            val stmt = conn!!.prepareStatement("UPDATE TRABAJADORES SET NOMBRE = ?, APELLIDOS = ?, FECHA_NAC = ? WHERE DNI = ?")
            stmt.setString(1, trabajador.nombreTrabajador)
            stmt.setString(2, trabajador.apellidosTrabajador)
            stmt.setString(3, trabajador.fecha_nacTrabajador)
            stmt.setString(4, dni)
            val rowsUpdated = stmt.executeUpdate()
            return if (rowsUpdated>0){
                "Se ha actualizado correctamente"
            }else{
                throw Exception()
            }
        }catch (e:Exception){
            return "No se han encontrado resultados"
        }
    }

    /**
     * Recupera todos los trabajadores que hay en la base de datos
     * @return string con todos los trabajadores con sus datos
     */
    fun recuperarTrabajadores() : String{
        try {
            val stmt = conn!!.prepareStatement("SELECT DNI , NOMBRE , APELLIDOS , FECHA_NAC FROM TRABAJADORES")
            val result = stmt.executeQuery()
            var busqueda = ""
            while (result.next()){
                val dniObtenido = result.getString("DNI")
                val nombreObtenido = result.getString("NOMBRE")
                val apellidosObtenidos = result.getString("APELLIDOS")
                val fechaObtenido = result.getString("FECHA_NAC")
                val trabajadorBuscado = Trabajador(dniObtenido,nombreObtenido,apellidosObtenidos,fechaObtenido)
                busqueda += trabajadorBuscado.toString()
            }
            return if (busqueda == "") throw Exception() else busqueda
        }catch (e:Exception){
            return "No se encontraron resultados"
        }
    }
}