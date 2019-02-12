import es.uam.eps.multij.Jugador
import es.uam.eps.multij.JugadorAleatorio
import es.uam.eps.multij.Partida
import java.io.File


class MenuConecta4() {
    val NUEVA_PARTIDA = 1
    val CARGAR = 2
    val SALIR = 3

    val TABLERO = 0
    val TURNO = 1
    val ULTIMO_MOVIMIENTO = 2
    val JUGADOR_1 = 3
    val JUGADOR_2 = 4
    val FICHAS = 5

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
        val jugadores = arrayListOf<Jugador> ()
        val tablero = mutableListOf<MutableList<Int>>()
        when (opcion) {
            NUEVA_PARTIDA -> {
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

                val informacionPartida = File(this.seleccionarPartidaGuardada()).bufferedReader().readLines()
                /*if (informacionPartida[TURNO].toInt() > 0) {
                    jugadores += JugadorConecta4Humano(
                        informacionPartida[JUGADOR_2]
                    )
                    jugadores += JugadorAleatorio(
                        informacionPartida[JUGADOR_1]
                    )
                } else {
                    jugadores += JugadorAleatorio(
                        informacionPartida[JUGADOR_1]
                    )
                    jugadores += JugadorConecta4Humano(
                        informacionPartida[JUGADOR_2]
                    )
                }*/
                jugadores += JugadorConecta4Humano(
                    informacionPartida[JUGADOR_2]
                )
                jugadores += JugadorAleatorio(
                    informacionPartida[JUGADOR_1]
                )

                val partida = Partida(TableroConecta4(tablero, informacionPartida[TURNO].toInt(),
                    MovimientoConecta4(informacionPartida[ULTIMO_MOVIMIENTO].toInt()),
                    informacionPartida[FICHAS]), jugadores)
                partida.tablero.stringToTablero(informacionPartida[TABLERO])
                partida.addObservador(ObservadorConecta4())
                partida.comenzar()
            }
            SALIR -> println("Hasta la próxima.")
        }
    }

    fun seleccionarPartidaGuardada(): String {
        var cont = 0
        var partida: Int?
        val path = System.getProperty("user.dir")
        var lista = mutableListOf<String>()
        File(path).list().forEach {
            if (it.endsWith(".txt")) {
                cont++
                println("${cont} - ${it}")
                lista.add(it)
            }
        }
        do {
            print("Seleccione una partida guardada: ")
            partida = readLine()!!.toIntOrNull()
        } while(partida !in 1..cont)

        if (partida != null) {
            return (lista.get(partida-1))
        }
        return ""
    }
}