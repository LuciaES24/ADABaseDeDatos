package Modelo

import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*

class GestorBBDD {

    var conn : Connection? = null

    var log = File(".log")

    var escribirLog = ""

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
            escribirLog += "\nCONNECT ${Date()} | Se ha conectado a la base de datos\n-\n"
        }catch (e: SQLException) {
            escribirLog += "\nERROR CONNECT ${Date()} | Error en la conexión: ${e.message}\n-\n"
        } catch (e: ClassNotFoundException) {
            escribirLog += "\nERROR CONNECT ${Date()} | No se encontró el driver JDBC: ${e.message}\n-\n"
        }
    }

    /**
     * Cierra la conexión de la base de datos
     */
    fun cerrarBBDD(){
        conn!!.close()
        escribirLog += "EXIT ${Date()} | Se ha cerrado la base de datos\n-\n"
        log.appendText(escribirLog)
    }

    /**
     * Añade un trabajador a la base de datos
     * @param trabajador objeto Trabajador con sus datos completos
     */
    fun agregarTrabajadorBaseDeDatos(trabajador: Trabajador): String {
        var resultado = ""
        try {
            val stmt = conn!!.prepareStatement("SELECT DNI FROM TRABAJADORES WHERE DNI = ?")
            stmt.setString(1,trabajador.dniTrabajador)
            var result = stmt.executeQuery()
            var comprobar = ""
            while (result.next()){
                comprobar+= result.getString("DNI")
            }
            if (comprobar==""){
                val stmtIns = conn!!.prepareStatement("INSERT INTO TRABAJADORES (DNI , NOMBRE , APELLIDOS , FECHA_NAC ) VALUES (?, ?, ?, ?)")
                stmtIns.setString(1, trabajador.dniTrabajador)
                stmtIns.setString(2,trabajador.nombreTrabajador)
                stmtIns.setString(3,trabajador.apellidosTrabajador)
                stmtIns.setString(4,trabajador.fecha_nacTrabajador)
                stmtIns.executeUpdate()
                resultado = "Trabajador guardado correctamente"
                escribirLog += "INSERT ${Date()} | Se ha introducido el trabajador ${trabajador.toString()}\n-\n"
            }else{
                throw Exception()
            }
        }catch (e:Exception){
            escribirLog += "ERROR INSERT ${Date()} | La clave primaria ya se encuentra en la base de datos ${trabajador.toString()}\n-\n"
            resultado = "Ese DNI ya se encuentra en nuestro registro"
        }
        return resultado
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
            if (busqueda == ""){
                throw Exception()
            }
            else{
                escribirLog += "SELECT ${Date()} | Se ha encontrado el trabajador con DNI: $dni | $busqueda\n-\n"
                return busqueda
            }
        }catch (e:Exception){
            escribirLog += "ERROR SELECT ${Date()} | No se encontro el DNI: $dni en la base de datos\n-\n"
            return "No se encontraron resultados"
        }
    }

    /**
     * Elimina un trabajador de la base de datos
     * @param dni string con el DNI del trabajador que queremos eliminar
     */
    fun eliminarTrabajador(dni: String):String{
        var resultado = ""
        try {
            val stmt = conn!!.prepareStatement("SELECT DNI FROM TRABAJADORES WHERE DNI = ?")
            stmt.setString(1,dni)
            var result = stmt.executeQuery()
            var comprobar = ""
            while (result.next()){
                comprobar+= result.getString("DNI")
            }
            if (comprobar!=""){
                val stmtDel = conn!!.prepareStatement("DELETE FROM TRABAJADORES WHERE DNI = ?")
                stmtDel.setString(1, dni)
                stmtDel.executeUpdate()
                resultado = "El trabajador se ha eliminado correctamente"
                escribirLog += "DELETE ${Date()} | Se ha eliminado el trabajador con DNI : $dni\n-\n"
            }else{
                throw Exception()
            }
        }catch (e:Exception){
            resultado = "No se ha encontrado el trabajador"
            escribirLog += "ERROR DELETE ${Date()} | No se encontró el trabajador con DNI: $dni para eliminarlo\n-\n"
        }
        return resultado
    }

    /**
     * Actualiza los datos de un trabajador de la base de datos
     * @param dni string con el dni del trabajador al que queremos actualizarle los datos
     * @param trabajador objeto Trabajador con los nuevos datos
     * @return string con el resultado de la función
     */
    fun actualizarTrabajador(dni: String, trabajador: Trabajador):String{
        var resultado = ""
        try {
            val stmt = conn!!.prepareStatement("UPDATE TRABAJADORES SET NOMBRE = ?, APELLIDOS = ?, FECHA_NAC = ? WHERE DNI = ?")
            stmt.setString(1, trabajador.nombreTrabajador)
            stmt.setString(2, trabajador.apellidosTrabajador)
            stmt.setString(3, trabajador.fecha_nacTrabajador)
            stmt.setString(4, dni)
            val rowsUpdated = stmt.executeUpdate()
            if (rowsUpdated>0){
                resultado = "Se ha actualizado correctamente"
                escribirLog += "UPDATE ${Date()} | Se ha actualizado el trabajador con DNI : $dni con los datos ${trabajador.toString()}\n-\n"
            }else{
                throw Exception()
            }
        }catch (e:Exception){
            escribirLog += "ERROR UPDATE ${Date()} | No se pudo actualizar el trabajador con DNI : $dni con los datos ${trabajador.toString()}\n-\n"
            resultado ="No se han encontrado resultados"
        }
        return resultado
    }

    /**
     * Recupera todos los trabajadores que hay en la base de datos
     * @return string con todos los trabajadores con sus datos
     */
    fun recuperarTrabajadores() : String{
        var resultado = ""
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
            if (busqueda == ""){
                throw Exception()
            } else {
                escribirLog += "SELECT ${Date()} | Se ha realizado la búsqueda de todos los trabajadores\n-\n"
                resultado = busqueda
            }
        }catch (e:Exception){
            escribirLog += "ERROR SELECT ${Date()} | No se encontraron resultados\n-\n"
            resultado = "No se encontraron resultados"
        }
        return resultado
    }
}