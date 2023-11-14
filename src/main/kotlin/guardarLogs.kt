import java.io.File

fun guardarLog(log : String): File {
    var carpeta = File("Logs")
    if (!carpeta.exists()){
        carpeta.mkdir()
    }
    var log = File(carpeta,log)
    return log
}