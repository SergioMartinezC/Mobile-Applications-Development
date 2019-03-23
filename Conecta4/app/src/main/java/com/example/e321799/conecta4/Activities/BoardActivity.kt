package com.example.e321799.conecta4.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import es.uam.eps.multij.*
import JugadorConecta4Humano
import TableroConecta4
import android.content.Intent
import android.view.View
import com.example.e321799.conecta4.Model.JugadorConecta4CPU
import com.example.e321799.conecta4.R
import kotlinx.android.synthetic.main.activity_board.*


class BoardActivity : AppCompatActivity(), PartidaListener, View.OnClickListener {
    private lateinit var game: Partida
    private lateinit var board: TableroConecta4
    val MENU_PARTIDA = 0
    val MENU_PARTIDA_FINALIZADA = 1
    val MENU_FIN_PARTIDA = 2

    val BOARDSTRING = "es.uam.eps.dadm.er8.grid"

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
        setContentView(R.layout.activity_board)
        button_menu.setOnClickListener(this)

        startGame()
    }

    override fun onCambioEnPartida(e: Evento) {
        when(e.tipo) {
            Evento.EVENTO_CAMBIO -> updateUI()
            Evento.EVENTO_FIN -> {
                updateUI()
                val intent = Intent(this, PopUp::class.java)
                intent.putExtra("menu", MENU_FIN_PARTIDA)
                startActivity(intent)
            }
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

    override fun onClick(view: View) {
        when(view?.id) {
            R.id.button_menu-> {
                val intent = Intent(this, PopUp::class.java)
                if (game.tablero.estado == Tablero.FINALIZADA || game.tablero.estado == Tablero.TABLAS) {
                    intent.putExtra("menu", MENU_PARTIDA_FINALIZADA)
                } else {
                    intent.putExtra("menu", MENU_PARTIDA)
                }
                startActivity(intent)
            }
        }
    }

    private fun checkCPUColor() {
        var jugador1 = game.getJugador(board.JUGADOR_1 - 1)
        var jugador2 = game.getJugador(board.JUGADOR_2 - 1)
        if (jugador1 is JugadorConecta4CPU) {
            if (jugador2 is JugadorConecta4Humano) {
                while(jugador1.drawable == jugador2.drawable) {
                    setCPUColor(jugador1)
                }
            }
        }
        else if (jugador1 is JugadorConecta4Humano){
            if (jugador2 is JugadorConecta4CPU) {
                while(jugador1.drawable == jugador2.drawable) {
                    setCPUColor(jugador2)
                }
            }
        }
    }

    private fun setCPUColor(jugador: JugadorConecta4CPU) {
        var random = (0 until drawableIds.size).shuffled().last()
        jugador.drawable = drawableIds[random]
    }

    private fun startGame() {
        val jugadores = arrayListOf<Jugador>()
        val tablero = mutableListOf<MutableList<Int>>()

        jugadores += JugadorConecta4Humano(
            "Humano"
        )
        jugadores += JugadorConecta4CPU(
            "Aleatorio"
        )
        board = TableroConecta4(tablero)
        game = Partida(board, jugadores)
        game.addObservador(this)
        for (jugador in jugadores) {
            if (jugador is JugadorConecta4Humano) {
                registerListeners(jugador)
                jugador.setPartida(game)
                jugador.drawable = intent.extras.getInt("drawable")
            }
        }
        checkCPUColor()
        game.comenzar()
    }


    private fun registerListeners(jugador: JugadorConecta4Humano) {
        var button: ImageButton
        for (i in 0 until ids.size){
            for (j in 0 until ids[i].size) {
                button = findViewById(ids[i][j])
                button.setOnClickListener(jugador)
            }
        }
    }

    fun updateUI() {
        val tablero = board
        for (i in 0 until ids.size) {
            for (j in 0 until ids[i].size) {
                val button = findViewById<ImageButton>(ids[i][j])
                if (tablero is TableroConecta4) {
                    button.update(tablero, i, j, game)
                }

            }
        }
    }
}
