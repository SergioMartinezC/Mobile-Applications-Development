import es.uam.eps.multij.Jugador
import es.uam.eps.multij.AccionMover
import es.uam.eps.multij.Evento
import es.uam.eps.multij.Tablero
import java.io.File
import java.lang.NumberFormatException
import java.lang.management.MonitorInfo

/*Solo pido por pantalla la columna donde quiere poner una ficha, podríamos poner una variable que designe el
* tipo de ficha para cada jugador*/
class JugadorConecta4Humano(var name: String) : Jugador {
    val ERROR = "ERROR"
    override fun getNombre() = name
    override fun puedeJugar(tablero: Tablero?): Boolean {
        return true
    }

    override fun onCambioEnPartida(evento: Evento?) {
        when(evento?.tipo){
            Evento.EVENTO_TURNO -> {
                try {
                    do {
                        println("Elige una columna:")
                        println("Además, 'G' o 'Guardar' para guardar y 'S' o 'Salir' para salir de la partida")
                        print("->")
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
                            print("Elija un nombre para el guardado: ")
                            val nombrePartida = readLine() + ".txt"
                            val tablero = evento.partida.tablero
                            val movimiento = evento.partida.tablero.ultimoMovimiento
                            if (tablero is TableroConecta4 && movimiento is MovimientoConecta4) {
                                File(nombrePartida).writeText(evento.partida.tablero.tableroToString() + "\n"
                                        + evento.partida.tablero.turno + "\n" + movimiento.columna + "\n"
                                        + evento.partida.getJugador(0).nombre + "\n" + evento.partida.getJugador(1).nombre + "\n"
                                        + tablero.fichasEnColumna.toString().replace("{", "").replace("}", "").replace(" ", ""))
                            }
                            MenuConecta4().menuPrincipal(MenuConecta4().getOpcion())
                        } else if (comando == "s" || comando == "salir") {
                            evento.partida.realizaAccion(AccionSalir(this))
                        }
                        else if (comando == "p") {
                            println(evento.partida.tablero.tableroToString())
                        }
                    }while (!comandoValido(comando.trim()))
                }catch (e: NumberFormatException) {
                    println(e.message)
                }
            }
        }
    }

    private fun comandoValido(comando: String): Boolean {
        return comando in "1".."7" || comando == "guardar" || comando == "g"
        || comando == "salir" || comando == "s"
    }
}