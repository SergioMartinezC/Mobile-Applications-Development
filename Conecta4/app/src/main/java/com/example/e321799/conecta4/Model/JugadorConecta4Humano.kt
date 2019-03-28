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
    var token_tag: Int = 0
    var drawable: Int = 0

    private lateinit var game: Partida
    private val ids = arrayOf(
        intArrayOf(
            R.id.button00,
            R.id.button01,
            R.id.button02,
            R.id.button03,
            R.id.button04,
            R.id.button05,
            R.id.button06
        ),
        intArrayOf(
            R.id.button10,
            R.id.button11,
            R.id.button12,
            R.id.button13,
            R.id.button14,
            R.id.button15,
            R.id.button16
        ),
        intArrayOf(
            R.id.button20,
            R.id.button21,
            R.id.button22,
            R.id.button23,
            R.id.button24,
            R.id.button25,
            R.id.button26
        ),
        intArrayOf(
            R.id.button30,
            R.id.button31,
            R.id.button32,
            R.id.button33,
            R.id.button34,
            R.id.button35,
            R.id.button36
        ),
        intArrayOf(
            R.id.button40,
            R.id.button41,
            R.id.button42,
            R.id.button43,
            R.id.button44,
            R.id.button45,
            R.id.button46
        ),
        intArrayOf(
            R.id.button50,
            R.id.button51,
            R.id.button52,
            R.id.button53,
            R.id.button54,
            R.id.button55,
            R.id.button56
        )
    )

    override fun getNombre() = name

    override fun puedeJugar(tablero: Tablero?) = true

    fun setPartida(game: Partida) {
        this.game = game
    }

    override fun onClick(view: View) {
        try {
            if (game.tablero.estado != Tablero.EN_CURSO) {
                /* que hacer que hacer */
                return
            }
            println("Creamos movimiento ${view.id}")
            val movimiento = MovimientoConecta4(fromViewToColumn(view))
            if (game.tablero.esValido(movimiento)) {
                game.realizaAccion(AccionMover(this, movimiento))
            } else {
                /* que hacer que hacer */
            }

        } catch (e: Exception) {
           /* TO DO */
        }
    }

    private fun fromViewToColumn(view: View): Int {
        for (ficha in 0 until ids.size)
            for (columna in 0 until ids[ficha].size)
                if (view.id == ids[ficha][columna])
                    return columna
        return -1
    }

    override fun onCambioEnPartida(evento: Evento?) {
    }

}