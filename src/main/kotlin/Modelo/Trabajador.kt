package Modelo

class Trabajador(dni:String,nombre:String,apellidos:String,fecha_nac:String) {
    var dniTrabajador = dni
    var nombreTrabajador = nombre
    var apellidosTrabajador = apellidos
    var fecha_nacTrabajador = fecha_nac

    /**
     * Imprime los datos del trabajador
     * @return los datos del trabajador
     */
    override fun toString(): String {
        return "Trabajador\nDNI = '$dniTrabajador'\nNombre = '$nombreTrabajador'\nApellidos = '$apellidosTrabajador'\nFecha de nacimiento = '$fecha_nacTrabajador'\n"
    }


}