package com.example.e321799.conecta4.Activities


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.e321799.conecta4.Model.Round
import com.example.e321799.conecta4.Model.RoundRepository
import android.util.Log

import com.example.e321799.conecta4.R
import es.uam.eps.multij.*
import kotlinx.android.synthetic.main.activity_round.*
import JugadorConecta4Humano

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            round = RoundRepository.getRound(it.getString(ARG_ROUND_ID))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_round, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        round_title.text = round.title
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

    override fun onStart() {
        super.onStart()
        startRound()
    }
    override fun onResume() {
        super.onResume()
        view?.update(round)
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
        view?.setPlayerAsOnClickListener(localPlayer)
        if (game.tablero.estado == Tablero.EN_CURSO)
            game.comenzar()
    }

    override fun onCambioEnPartida(evento: Evento) {
        when (evento.tipo) {
            Evento.EVENTO_CAMBIO -> view?.update(round)
            Evento.EVENTO_FIN -> {
                view?.update(round)
            }
        }
    }
}
