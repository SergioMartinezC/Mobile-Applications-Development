import es.uam.eps.multij.*
import java.io.File
import java.lang.NumberFormatException

/*Solo pido por pantalla la columna donde quiere poner una ficha, podríamos poner una variable que designe el
* tipo de ficha para cada jugador*/
class JugadorConecta4Humano(var name: String) : Jugador {
    val ERROR = "ERROR"
    val GUARDADA = "GUARDADA"
    val MAX_NOMBRE_PARTIDA = 50
    override fun getNombre() = name
    override fun puedeJugar(tablero: Tablero?): Boolean {
        return true
    }

    override fun onCambioEnPartida(evento: Evento?) {
        when(evento?.tipo){
            Evento.EVENTO_TURNO -> {
                try {
                    do {
                        println("\nElige una columna:")
                        println("Además, 'G' o 'Guardar' para guardar y 'S' o 'Salir' para salir de la partida")
                        print("-> ")
                        var comando: String = readLine().toString().toLowerCase()
                        if (comando in "1".."7") {
                            try {
                                val movimiento = MovimientoConecta4(comando.toInt().dec())
                                if (evento.partida.tablero.esValido(movimiento)) {
                                    evento.partida.realizaAccion(AccionMover(this, movimiento))
                                } else {
                                    comando = ERROR
                                }
                            } catch (e: NumberFormatException) {
                                println(e.message)
                                comando = ERROR
                            }

                        } else if (comando == "g" || comando == "guardar") {
                            var nombrePartida = pedirNombrePartida()
                            val tablero = evento.partida.tablero
                            val movimiento = evento.partida.tablero.ultimoMovimiento
                            if (tablero is TableroConecta4 && movimiento is MovimientoConecta4) {
                                File(nombrePartida).writeText(evento.partida.tablero.tableroToString() + "\n"
                                        + evento.partida.tablero.turno + "\n" + movimiento.columna + "\n"
                                        + evento.partida.getJugador(0).nombre + "\n"
                                        + evento.partida.getJugador(1).nombre + "\n"
                                        + tablero.fichasEnColumna.toString().replace("{", "")
                                    .replace("}", "").replace(" ", "")
                                + "\n" + evento.partida.tablero.numJugadas)
                            }
                            comando = GUARDADA
                        } else if (comando == "s" || comando == "salir") {
                            MenuConecta4().menuPrincipal(MenuConecta4().getOpcion())
                        }
                    }while (!comandoValido(comando.trim()))
                }catch (e: NumberFormatException) {
                    println(e.message)
                }
            }
        }
    }


    fun confirmaSobreescritura(): Boolean {
        var sobreescribe: Int?
        do {
            println("Ya existe una partida con ese nombre, ¿desea sobreescribir?:")
            println("1 - Sí\n2 - No")
            sobreescribe = readLine()?.toIntOrNull()
        } while(sobreescribe !in 1..2)
        return sobreescribe == 1
    }

    fun longitudNombreMayorMax(nombrePartida: String?): Boolean {
        if (nombrePartida != null) {
            if (nombrePartida.length > MAX_NOMBRE_PARTIDA) {
                println("Nombre demasiado largo, no más de $MAX_NOMBRE_PARTIDA caracteres")
                return true
            }
        }
        return false
    }

    fun pedirNombrePartida(): String? {
        val path = System.getProperty("user.dir")
        var lista = mutableListOf<String>()
        var nombrePartida: String?
        File(path).list().forEach {
            if (it.endsWith(".txt")) {
                lista.add(it)
            }
        }

        do {
            print("Elija un nombre para el guardado: ")
            nombrePartida = readLine() + ".txt"
        } while((nombrePartida in lista && !confirmaSobreescritura()) || longitudNombreMayorMax(nombrePartida))
        return nombrePartida
    }

    private fun comandoValido(comando: String): Boolean {
        return comando in "1".."7" || comando == "guardar" || comando == "g"
        || comando == "salir" || comando == "s"
    }
}