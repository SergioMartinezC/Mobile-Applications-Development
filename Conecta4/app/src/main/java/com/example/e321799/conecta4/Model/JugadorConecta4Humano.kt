import android.view.View
import com.example.e321799.conecta4.R
import es.uam.eps.multij.*
import java.io.File
import java.lang.Exception
import java.lang.NumberFormatException


/*Solo pido por pantalla la columna donde quiere poner una ficha, podríamos poner una variable que designe el
* tipo de ficha para cada jugador*/
class JugadorConecta4Humano(var name: String) : Jugador, View.OnClickListener {
    val ERROR = "ERROR"
    val GUARDADA = "GUARDADA"
    val MAX_NOMBRE_PARTIDA = 50

    private lateinit var game: Partida

    private val ids = arrayOf(
        intArrayOf(R.id.button00, R.id.button01, R.id.button02, R.id.button03, R.id.button04, R.id.button05),
        intArrayOf(R.id.button10, R.id.button11, R.id.button12, R.id.button13, R.id.button14, R.id.button15),
        intArrayOf(R.id.button20, R.id.button21, R.id.button22, R.id.button23, R.id.button24, R.id.button25),
        intArrayOf(R.id.button30, R.id.button31, R.id.button32, R.id.button33, R.id.button34, R.id.button35),
        intArrayOf(R.id.button40, R.id.button41, R.id.button42, R.id.button43, R.id.button44, R.id.button45),
        intArrayOf(R.id.button50, R.id.button51, R.id.button52, R.id.button53, R.id.button54, R.id.button55)
    )

    override fun getNombre() = name

    override fun puedeJugar(tablero: Tablero?) = true

    fun setPartida(game: Partida) {
        this.game = game
    }

    override fun onClick(view: View) {
        try {
            if (game.tablero.estado != Tablero.EN_CURSO) {
                 /* CHOU DE QUE LA PARTIDA NO ESTA EN CURSO */
                return
            }
            val movimiento = MovimientoConecta4(fromViewToColumn(view))
            if (game.tablero.esValido(movimiento)) {
                game.realizaAccion(AccionMover(this, movimiento))
            } else {
                /* que hacer que hacer */
            }

        } catch (e: Exception) {
            /* que no funciona loco */
        }
    }

    private fun fromViewToColumn(view: View): Int {
        for (ficha in 0 until ids.size)
            for (columna in 0 until ids.size)
                if (view.id == ids[ficha][columna])
                    return columna
        return -1
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