import es.uam.eps.multij.Jugador
import es.uam.eps.multij.JugadorAleatorio
import es.uam.eps.multij.Partida
import java.io.File


class MenuConecta4() {
    val NUEVA_PARTIDA = 1
    val CARGAR = 2
    val SALIR = 3

    fun getOpcion(): Int? {
        var opcion: Int?
        do {

            println("1 - NUEVA PARTIDA")
            println("2 - CARGAR PARTIDA")
            println("3 - SALIR DEL JUEGO")
            print("Elija una opción: ")
            opcion = readLine()!!.toIntOrNull()
            if (opcion !in 1..3) {
                println("Opción incorrecta.")
            }
            println("")
        } while (opcion !in 1..3)
        return opcion

    }

    fun menuPrincipal(opcion: Int?) {
        when (opcion) {
            NUEVA_PARTIDA -> {
                val jugadores = arrayListOf<Jugador> ()
                val tablero = mutableListOf<MutableList<Int>>()
                jugadores += JugadorAleatorio(
                    "Aleatorio"
                )
                jugadores += JugadorConecta4Humano(
                    "Humano"
                )
                val partida = Partida(TableroConecta4(tablero), jugadores)
                partida.addObservador(ObservadorConecta4())
                partida.comenzar()
            }
            CARGAR -> {
                val informacionPartida = File("prueba.txt").bufferedReader().readLines()
                val jugadores = arrayListOf<Jugador> ()
                val tablero = mutableListOf<MutableList<Int>>()
                jugadores += JugadorAleatorio(
                    informacionPartida[3]
                )
                jugadores += JugadorConecta4Humano(
                    informacionPartida[4]
                )
                val partida = Partida(TableroConecta4(tablero, informacionPartida[1].toInt(),
                    MovimientoConecta4(informacionPartida[2].toInt()), informacionPartida[5]), jugadores)
                partida.tablero.stringToTablero(informacionPartida[0])
                partida.addObservador(ObservadorConecta4())
                partida.comenzar()
            }
            SALIR -> print("Hasta la próxima.")
        }
    }

}