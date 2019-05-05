import android.content.Context
import android.media.MediaPlayer
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
 * @brief Jugador humano de la aplicación
 * @param name Nombre del jugador.
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
     * @brief Asigna una partida al jugador
     * @param game partida
     */
    fun setPartida(game: Partida) {
        this.game = game
    }

    /**
     * @brief Realiza la acción de jugar sobre la columna [column] en caso de que la partida este en curso
     * @param column columna
     * @param view vista
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
                    var mp = MediaPlayer.create(view.context, R.raw.plop)
                    mp.start()
                    game.realizaAccion(AccionMover(this, m))
                }
                else {
                    Snackbar.make(view, R.string.invalid_turn, Snackbar.LENGTH_SHORT).show()
                }
            } else {
                game.realizaAccion(AccionMover(this, m))
                var mp = MediaPlayer.create(view.context, R.raw.plop)
                mp.start()
            }
        } else {
            Snackbar.make(view, R.string.invalid_movement, Snackbar.LENGTH_SHORT).show()
        }
    }

    /**
     * @brief Funcion que es ejecutada cada vez que se recibe [evento]
     * @param evento evento
     */
    override fun onCambioEnPartida(evento: Evento?) {

    }

}