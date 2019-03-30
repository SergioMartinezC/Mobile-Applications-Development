package com.example.e321799.conecta4.Activities


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.e321799.conecta4.Model.Round
import com.example.e321799.conecta4.Model.RoundRepository

import com.example.e321799.conecta4.R
import es.uam.eps.multij.*
import kotlinx.android.synthetic.main.activity_round.*
import JugadorConecta4Humano
import android.support.design.widget.FloatingActionButton
import android.widget.TextView
import com.example.e321799.conecta4.views.ERButton
import com.example.e321799.conecta4.views.ERView
import kotlinx.android.synthetic.main.fragment_round.*
import java.lang.RuntimeException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class RoundFragment : Fragment(), PartidaListener {

    private lateinit var game: Partida
    private lateinit var round: Round
    private lateinit var board_erview: ERView

    var listener: OnRoundFragmentInteractionListener? = null
    interface OnRoundFragmentInteractionListener {
        fun onRoundUpdated()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            arguments?.let {
                round = RoundRepository.getRound(it.getString(ARG_ROUND_ID))
            }
        }catch (e : Exception) {
            e.printStackTrace()
            activity?.finish()
        }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnRoundFragmentInteractionListener) {
            listener = context
        }
        else {
            throw RuntimeException(context.toString() + " must implement OnRoundInteractionLIstener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_round, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerResetButton()
        val titleView = view.findViewById<TextView>(R.id.round_title)
        titleView.text = round.title
    }


    companion object {
        val ARG_ROUND_ID = "es.uam.eps.dadm.er10.round_id"
        @JvmStatic
        fun newInstance(round_id: String) =
            RoundFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ROUND_ID, round_id)
                }
            }
    }

    private fun registerResetButton() {
        val resetButton = view!!.findViewById(R.id.reset_round_fab) as
                FloatingActionButton
        resetButton.setOnClickListener(View.OnClickListener {
            if (round.board.estado !== Tablero.EN_CURSO) {
                return@OnClickListener
            }
            round.board.reset()
            startRound()
            board_erview.invalidate()
            listener?.onRoundUpdated()
        })
    }

    override fun onStart() {
        super.onStart()
        startRound()
    }
    override fun onResume() {
        super.onResume()
        board_erview.invalidate()
    }

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


    override fun onCambioEnPartida(evento: Evento) {
        when (evento.tipo) {
            Evento.EVENTO_CAMBIO ->  {
                board_erview.invalidate()
                listener?.onRoundUpdated()
            }
            Evento.EVENTO_FIN -> {
                board_erview.invalidate()
                listener?.onRoundUpdated()
                AlertDialogFragment().show(activity?.supportFragmentManager,
                    "ALERT_DIALOG")
            }
        }
    }
}
