package com.example.e321799.conecta4.activities

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.e321799.conecta4.model.Round
import com.example.e321799.conecta4.model.RoundRepository
import com.example.e321799.conecta4.R
import es.uam.eps.multij.*
import JugadorConecta4Humano
import android.support.design.widget.FloatingActionButton
import android.util.Log
import android.widget.TextView
import com.example.e321799.conecta4.views.ERView
import kotlinx.android.synthetic.main.fragment_round.*
import java.lang.RuntimeException


/**
 * Fragmento utilizado para mostrar la partida
 *
 */
class RoundFragment : Fragment(), PartidaListener {

    private lateinit var game: Partida
    private lateinit var round: Round
    private lateinit var board_erview: ERView

    var listener: OnRoundFragmentInteractionListener? = null

    /**
     * Interfaz que interactua con el fragmento
     */
    interface OnRoundFragmentInteractionListener {
        /**
         * Funcion que es llamada cuando el tablero de la ronda se actualiza
         */
        fun onRoundUpdated(round: Round)
    }

    /**
     * Se crea el fragmento con la ronda seleccionada
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            arguments?.let {
                round = Round.fromJSONString(it.getString(ARG_ROUND))
            }
        } catch (e: Exception) {
            Log.d("DEBUG", e.message)
            activity?.finish()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(BOARDSTRING, round.board.tableroToString())
        super.onSaveInstanceState(outState)
    }

    /**
     * Funcion que es llamada cuando se asigna el fragmento a la vista
     */
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnRoundFragmentInteractionListener) {
            listener = context
        }
        else {
            throw RuntimeException(context.toString() + " must implement OnRoundInteractionLIstener")
        }
    }

    /**
     * Funcion que es llamada cuando se desacopla el fragmento de la vista
     */
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * Funcion que se llama cuando la vista a la que pertenece el fragmento se crea
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_round, container, false)
    }

    /**
     * Funcion que se llama cuando la vista a la que pertenece el fragmento se crea
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        round_title.text = "${round.title}"
        if (savedInstanceState != null) {
            round.board.stringToTablero(savedInstanceState.getString(BOARDSTRING))
        }
        registerResetButton(view)
    }

    /**
     * Metodo de factoria del fragmento que crea una instancia con la ronda seleccionada
     */
    companion object {
        val ARG_ROUND = "es.uam.eps.dadm.er20.round"
        val BOARDSTRING = "es.uam.eps.dadm.er20.boardstring"
        @JvmStatic
        fun newInstance(round: String) =
            RoundFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ROUND, round)
                }
            }
    }

    /**
     * Funcion que asigna un metodo onClick al botón que resetea el tablero
     */
    private fun registerResetButton(view: View) {
        val resetButton = view!!.findViewById(R.id.reset_round_fab) as
                FloatingActionButton
        resetButton.setOnClickListener(View.OnClickListener {
            if (round.board.estado !== Tablero.EN_CURSO) {
                return@OnClickListener
            }
            round.board.reset()
            startRound()
            board_erview.invalidate()
            listener?.onRoundUpdated(round)
        })
    }

    /**
     *
     */
    override fun onStart() {
        super.onStart()
        startRound()
    }

    /**
     *
     */
    override fun onResume() {
        super.onResume()
        board_erview.invalidate()
    }

    /**
     * Funcion que inicia una partida completa desde 0
     */
    internal fun startRound() {
        val players = ArrayList<Jugador>()
        val localPlayer = JugadorConecta4Humano("Humano")
        val randomPlayer = JugadorAleatorio("Random player")
        players.add(localPlayer)
        players.add(randomPlayer)

        game = Partida(round.board, players)
        game.addObservador(this)
        localPlayer.setPartida(game)
        board_erview = view!!.findViewById(R.id.board_erview) as ERView
        board_erview.setBoard(round.board)
        board_erview.setOnPlayListener(localPlayer)
        if (game.tablero.estado == Tablero.EN_CURSO) {
            game.comenzar()
        }
    }

    /**
     * Funcion que es llamada cuando se produce un evento [evento]
     */
    override fun onCambioEnPartida(evento: Evento) {
        when (evento.tipo) {
            Evento.EVENTO_CAMBIO ->  {
                board_erview.invalidate()
                listener?.onRoundUpdated(round)
            }
            Evento.EVENTO_FIN -> {
                board_erview.invalidate()
                listener?.onRoundUpdated(round)
                AlertDialogFragment().show(activity?.supportFragmentManager,
                    "ALERT_DIALOG")
            }
        }
    }
}
