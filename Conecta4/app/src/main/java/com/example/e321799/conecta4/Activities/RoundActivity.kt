package com.example.e321799.conecta4.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.e321799.conecta4.R
import TableroConecta4
import JugadorConecta4Humano
import android.content.Context
import android.content.Intent
import com.example.e321799.conecta4.Model.Round
import com.example.e321799.conecta4.Model.RoundRepository
import es.uam.eps.multij.*
import kotlinx.android.synthetic.main.activity_round.*

class RoundActivity : AppCompatActivity(), PartidaListener {
    val BOARDSTRING = "es.uam.eps.dadm.er8.grid"
    private lateinit var game: Partida
    private lateinit var board: TableroConecta4
    private lateinit var round: Round

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round)
        val roundId = intent.extras.getString(EXTRA_ROUND_ID)
        round = RoundRepository.getRound(roundId)
        round_title.text = round.title
        startRound()
        if (savedInstanceState != null) {
            try {
                board.stringToTablero(savedInstanceState.getString(BOARDSTRING))
                updateUI()
            } catch (e: ExcepcionJuego) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        val EXTRA_ROUND_ID = "es.uam.eps.dadm.er10.round_id"
        fun newIntent(packageContext: Context, roundId: String): Intent {
            val intent = Intent(packageContext, RoundActivity::class.java)
            intent.putExtra(EXTRA_ROUND_ID, roundId)
            return intent
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString(BOARDSTRING, board.tableroToString())
        super.onSaveInstanceState(outState)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        try {
            if (savedInstanceState?.getString(BOARDSTRING) != null) {
                board.stringToTablero(savedInstanceState.getString(BOARDSTRING))
            }
            updateUI()
        } catch (e: ExcepcionJuego) {
            e.printStackTrace()
        }
    }

    private fun registerListeners(localPlayer: JugadorConecta4Humano) {
        for (i in 0 until ids.size)
            for (j in 0 until ids[0].size) {
                val button = findViewById<ImageButton>(ids[i][j])
                button.setOnClickListener(localPlayer)
            }
    }
    private fun startRound() {
        val players = ArrayList<Jugador>()
        val localPlayer = JugadorConecta4Humano("Humano")
        val randomPlayer = JugadorAleatorio("Random player")
        players.add(localPlayer)
        players.add(randomPlayer)
        board = TableroConecta4()
        game = Partida(board, players)
        game.addObservador(this)
        localPlayer.setPartida(game)
        registerListeners(localPlayer)
        if (game.tablero.estado == Tablero.EN_CURSO)
            game.comenzar()
    }
    fun updateUI() {
        for (i in 0 until ids.size)
            for (j in 0 until ids[0].size) {
                val button = findViewById<ImageButton>(ids[i][j])
                button.update(board, i, j)
            }
    }

    override fun onCambioEnPartida(evento: Evento) {
        when (evento.tipo) {
            Evento.EVENTO_CAMBIO -> updateUI()
            Evento.EVENTO_FIN -> {
                updateUI()
            }
        }
    }
}
