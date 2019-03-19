package com.example.e321799.conecta4.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import es.uam.eps.multij.*
import JugadorConecta4Humano
import TableroConecta4
import ObservadorConecta4
import android.app.ActionBar
import android.content.Intent
import android.widget.Button
import com.example.e321799.conecta4.R


class MainActivity : AppCompatActivity(), PartidaListener {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)
        startGame()
    }

    override fun onCambioEnPartida(e: Evento) {
        when(e.tipo) {
            Evento.EVENTO_CAMBIO -> updateUI()
            Evento.EVENTO_FIN -> {
                updateUI()
                val intent = Intent(this, EndGamePopUp::class.java)
                startActivity(intent)
            }
        }
    }

    private fun startGame() {
        val jugadores = arrayListOf<Jugador>()
        val tablero = mutableListOf<MutableList<Int>>()

        jugadores += JugadorConecta4Humano(
            "Humano"
        )
        jugadores += JugadorAleatorio(
            "Aleatorio"
        )
        game = Partida(TableroConecta4(tablero), jugadores)
        game.addObservador(this)
        for (jugador in jugadores) {
            if (jugador is JugadorConecta4Humano) {
                registerListeners(jugador)
                jugador.setPartida(game)
            }
        }
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
        val tablero = game.tablero
        for (i in 0 until ids.size) {
            for (j in 0 until ids[i].size) {
                val button = findViewById<ImageButton>(ids[i][j])
                if (tablero is TableroConecta4) {
                    button.update(tablero, i, j)
                }

            }
        }
    }
}
