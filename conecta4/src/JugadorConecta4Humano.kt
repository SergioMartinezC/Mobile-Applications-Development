import es.uam.eps.multij.Jugador
import es.uam.eps.multij.AccionMover
import es.uam.eps.multij.Evento
import es.uam.eps.multij.Tablero
import java.lang.NumberFormatException
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
                            val movimiento = MovimientoConecta4(comando.toInt().dec())
                            if (evento.partida.tablero.esValido(movimiento)) {
                                evento.partida.realizaAccion(AccionMover(this, movimiento))
                            } else {
                                comando = ERROR
                            }
                        } else if (comando == "g" || comando == "guardar") {
                            evento.partida.realizaAccion(AccionGuardar(this))
                            println("GUARDAMOS PARTIDA")
                        } else if (comando == "s" || comando == "salir") {
                            evento.partida.realizaAccion(AccionSalir(this))
                        }
                        else if (comando == "p") {
                            println(evento.partida.tablero.tableroToString())
                        }
                    }while (!comandoValido(comando))
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