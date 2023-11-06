package Modelo

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class GestorBBDD {

    var conn : Connection? = null

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

    fun cerrarBBDD(conexion: Connection){
        conexion.close()
    }

    fun agregarTrabajadorBaseDeDatos(conexion: Connection, trabajador: Trabajador){
        val stmt = conexion.prepareStatement("INSERT INTO TRABAJADORES (DNI , NOMBRE , APELLIDOS , FECHA_NAC ) VALUES (?, ?, ?, ?)")
        stmt.setString(1, trabajador.dniTrabajador)
        stmt.setString(2,trabajador.nombreTrabajador)
        stmt.setString(3,trabajador.apellidosTrabajador)
        stmt.setString(4,trabajador.fecha_nacTrabajador)
        stmt.executeUpdate()
    }

    fun recuperarTrabajador(conexion: Connection, dni:String) : String{
        try {
            val stmt = conexion.prepareStatement("SELECT DNI , NOMBRE , APELLIDOS , FECHA_NAC FROM TRABAJADORES WHERE DNI = ?")
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

    fun eliminarTrabajador(conexion: Connection, dni: String){
        val stmt = conexion.prepareStatement("DELETE FROM TRABAJADORES WHERE DNI = ?")
        stmt.setString(1, dni)
        stmt.executeUpdate()
    }

    fun actualizarTrabajador(conexion: Connection, dni: String, trabajador: Trabajador):String{
        try {
            val stmt = conexion.prepareStatement("UPDATE TRABAJADORES SET NOMBRE = ?, APELLIDOS = ?, FECHA_NAC = ? WHERE DNI = ?")
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

    fun recuperarTrabajadores(conexion: Connection) : String{
        try {
            val stmt = conexion.prepareStatement("SELECT DNI , NOMBRE , APELLIDOS , FECHA_NAC FROM TRABAJADORES")
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