package Vista

import Modelo.Trabajador

class Vista {
    /**
     * Imprime las opciones del menu
     * @return int con la opción elegida por el usuario
     */
    fun imprimirMenu() : Int{
        println("Elija una opción:" +
                "\n1. Introducir trabajador" +
                "\n2. Actualizar trabajador" +
                "\n3. Eliminar trabajador" +
                "\n4. Buscar trabajador (DNI)" +
                "\n5. Mostrar todos los trabajadores" +
                "\n6. Salir" +
                "\n--> ")
    var eleccion = readln().toInt()
    return eleccion
    }

    /**
     * Pide los datos necesarios para insertar un trabajador a la base de datos
     * @return trabajador con los datos ingresados por el usuario
     */
    fun pedirAgregarDatos() : Trabajador{
        println("INTRODUCIR TRABAJADOR")

        println("Introduzca el DNI\n--> ")
        var dni = readln()
        println("Introduzca el nombre\n--> ")
        var nombre = readln()
        println("Introduzca los apellidos\n--> ")
        var apellidos = readln()
        println("Introduzca su fecha de nacimiento (DD/MM/YYYY)\n--> ")
        var fecha = readln()
        return Trabajador(dni,nombre,apellidos,fecha)
    }

    /**
     * Pide los datos necesarios para actualizar a un trabajador en la base de datos
     * @return Pair con un string con el dni y un objeto Trabajador con los nuevos datos
     */
    fun pedirActualizarDatos() : Pair<String,Trabajador>{
        println("ACTUALIZAR TRABAJADOR")
        println("Introduzca el dni del trabajador al que quiere actualizar los datos\n--> ")
        var dni = readln()

        println("Introduzca el nuevo nombre\n--> ")
        var nombre = readln()
        println("Introduzca los nuevos apellidos\n--> ")
        var apellidos = readln()
        println("Introduzca la nueva fecha de nacimiento\n--> ")
        var fecha = readln()

        return Pair(dni, Trabajador(dni,nombre,apellidos,fecha))
    }

    /**
     * Solicita el dni del trabajador que se quiere eliminar
     * @return string con el dni ingresado por el usuario
     */
    fun pedirDatosEliminar():String{
        println("ELIMINAR TRABAJADOR")
        println("Introduzca el DNI del trabajador que quiere eliminar\n--> ")
        var dni = readln()
        return dni
    }

    /**
     * Solicita el dni del trabajador que se quiere buscar
     * @return string con el dni ingresado por el usuario
     */
    fun pedirDatosBuscar() : String{
        println("BUSCAR TRABAJADOR (DNI)")
        println("Introduzca el DNI del trabajador que quiere buscar\n--> ")
        var dni = readln()
        return dni
    }

    /**
     * Imprime una despedida al usuario
     */
    fun salirMenu(){
        println("SALIR")
        println("Se ha cerrado correctamente")
    }
}