import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
import com.example.e321799.conecta4.R
import com.example.e321799.conecta4.activities.SettingsActivity
import com.example.e321799.conecta4.firebase.FBDataBase
import com.example.e321799.conecta4.model.RoundRepositoryFactory
import com.example.e321799.conecta4.views.ERView
import es.uam.eps.multij.*
import java.io.File
import java.lang.Exception
import java.lang.NumberFormatException


/**
 * Jugador humano de la aplicación
 *
 *
 *
 * @param T the type of a member in this group.
 * @property name Nombre del jugador.
 * @constructor Crea un jugador humano con el nombre
 */
class JugadorConecta4Humano(var name: String) : Jugador, ERView.OnPlayListener {
    var drawable: Int = 0

    private lateinit var game: Partida

    /**
     * Obtiene el nombre del jugador
     * @return Nombre del jugador
     */
    override fun getNombre() = name


    /**
     * Decide si un jugador puede jugar
     * @return True si puede jugar, false en caso contrario
     */
    override fun puedeJugar(tablero: Tablero?) =  true


    /**
     * Asigna la partida [game] al jugador
     */
    fun setPartida(game: Partida) {
        this.game = game
    }

    /**
     * Realiza la acción de jugar sobre la columna [column] en caso de que la partida este en curso
     *
     */
    override fun onPlay(column: Int, view: ERView) {
        if (game.tablero.estado != Tablero.EN_CURSO){
            return
        }
        val repository = RoundRepositoryFactory.createRepository(view.context)
        val m = MovimientoConecta4(column)

        if (game.tablero.esValido(m)) {
            if (repository is FBDataBase) {
                if (game.getJugador(game.tablero.turno).nombre == nombre) {
                    game.realizaAccion(AccionMover(this, m))
                }
                else {
                    Snackbar.make(view, R.string.invalid_turn, Snackbar.LENGTH_SHORT).show()
                }
            } else {
                game.realizaAccion(AccionMover(this, m))
            }
        } else {
            Snackbar.make(view, R.string.invalid_movement, Snackbar.LENGTH_SHORT).show()
        }
    }

    /**
     * Funcion que es ejecutada cada vez que se recibe [evento]
     */
    override fun onCambioEnPartida(evento: Evento?) {

    }

}