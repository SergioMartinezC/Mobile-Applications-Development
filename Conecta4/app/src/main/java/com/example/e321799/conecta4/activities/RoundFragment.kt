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
import android.app.Activity
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation
import android.preference.PreferenceManager
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.e321799.conecta4.database.DataBase
import com.example.e321799.conecta4.firebase.FBDataBase
import com.example.e321799.conecta4.model.RoundRepositoryFactory
import com.example.e321799.conecta4.views.ERView
import com.google.firebase.database.FirebaseDatabase
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
        println("--------------onSaveInstanceState-----------------")
        super.onSaveInstanceState(outState)
    }

    /**
     * Funcion que es llamada cuando se asigna el fragmento a la vista
     */
    override fun onAttach(context: Context?) {
        println("--------------onAttach-----------------")
        super.onAttach(context)
        if (context is OnRoundFragmentInteractionListener) {
            listener = context
        }
        else {
            throw RuntimeException(context.toString() + " must implement OnRoundInteractionLIstener")
        }
        val repository = RoundRepositoryFactory.createRepository(context!!)
        if (repository is FBDataBase) {
            val callback = object : RoundRepository.RoundsCallback {
                override fun onResponse(rounds: List<Round>) {
                    for (r in rounds) {
                        if (r.id == round.id) {
                            board_erview.setBoard(r.board)
                            round.board.copyBoard(r.board)
                            round.secondPlayerName = r.secondPlayerName
                            round.secondPlayerUUID = r.secondPlayerUUID
                        }
                    }
                    var token =  view?.findViewById<ImageView>(R.id.player_turn_token)
                    var name = view?.findViewById<TextView>(R.id.player_turn_name)
                    if (round.board.turno == 0) {
                        token?.setImageResource(R.drawable.token_blue_48dp)
                        name?.text = "Turno de ${round.firstPlayerName.replaceAfter("@", "").removeSuffix("@")}"

                    } else {
                        token?.setImageResource(R.drawable.token_red_48dp)
                        name?.text = "Turno de ${round.secondPlayerName.replaceAfter("@", "").removeSuffix("@")}"
                    }
                    board_erview.invalidate()
                    if (round.board.estado != Tablero.EN_CURSO) {
                        if (activity != null) {
                            var mensaje = "LOSER"
                            if (round.board.comprobarGanador() == 0) {
                                mensaje = "TABLAS"
                            } else if (round.board.comprobarGanador() == 1 && round.firstPlayerName == SettingsActivity.getPlayerName(context!!)) {
                                mensaje = "WINNER"
                            } else if (round.board.comprobarGanador() == 2 && round.secondPlayerName == SettingsActivity.getPlayerName(context!!)) {
                                mensaje = "WINNER"
                            }
                            AlertDialogFragment().show(activity?.supportFragmentManager,
                                mensaje)
                        }
                    }
                }
                override fun onError(error: String) {
                }
            }
            repository?.startListeningBoardChanges(callback)
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
        val repository = RoundRepositoryFactory.createRepository(context!!)
        super.onViewCreated(view, savedInstanceState)
        round_title.text = "${round.title}"
        if (savedInstanceState != null) {
            round.board.stringToTablero(savedInstanceState.getString(BOARDSTRING))
        }
        if (repository is DataBase) {
            val turnLayout = view!!.findViewById(R.id.player_turn) as
                    LinearLayout
            turnLayout.visibility = View.INVISIBLE
            registerResetButton(view)
        } else {
            val resetButton = view!!.findViewById(R.id.reset_round_fab) as
                    FloatingActionButton
            resetButton.hide()
        }
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
        val repository = RoundRepositoryFactory.createRepository(context!!)
        val players = ArrayList<Jugador>()
        val localPlayer = JugadorConecta4Humano(SettingsActivity.getPlayerName(context!!))
        //val randomPlayer = JugadorAleatorio("Random player")
        if (repository is FBDataBase) {
            if(repository.isOpenOrIamIn(round)){
            var secondPlayer = JugadorConecta4Humano("Humano")
                if (round.firstPlayerName == SettingsActivity.getPlayerName(context!!)){
                    secondPlayer = JugadorConecta4Humano(round.secondPlayerName)
                    players.add(localPlayer)
                    players.add(secondPlayer)
                } else if (round.secondPlayerName == SettingsActivity.getPlayerName(context!!)) {
                    secondPlayer = JugadorConecta4Humano(round.firstPlayerName)
                    players.add(secondPlayer)
                    players.add(localPlayer)
                } else {
                    secondPlayer = JugadorConecta4Humano(SettingsActivity.getPlayerName(context!!))
                    players.add(secondPlayer)
                    players.add(localPlayer)
                }
            }
        } else {
            var secondPlayer = JugadorConecta4Humano("NPC")
            players.add(localPlayer)
            players.add(secondPlayer)
        }


        game = Partida(round.board, players)
        game.addObservador(this)
        localPlayer.setPartida(game)
        /*secondPlayer.setPartida(game)*/
        board_erview = view!!.findViewById(R.id.board_erview) as ERView
        board_erview.setBoard(round.board)
        board_erview.setOnPlayListener(localPlayer)
        /*board_erview.setOnPlayListener(secondPlayer)*/
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
                var repository = RoundRepositoryFactory.createRepository(context!!)
                board_erview.invalidate()
                round.board.cambiaEstado(Tablero.TABLAS)
                listener?.onRoundUpdated(round)
                if (repository is DataBase) {
                    var mensaje = "LOSER"
                    if (round.board.comprobarGanador() == 0) {
                        mensaje = "TABLAS"
                    } else if (round.board.comprobarGanador() == 1) {
                        mensaje = "WINNER"
                    }
                    AlertDialogFragment().show(activity?.supportFragmentManager,
                        mensaje)
                }
            }
        }
    }
}
