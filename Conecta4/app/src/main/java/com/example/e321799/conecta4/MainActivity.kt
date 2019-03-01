package com.example.e321799.conecta4

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import es.uam.eps.multij.*
import JugadorConecta4Humano
import TableroConecta4
import ObservadorConecta4
import android.util.Log


class MainActivity : AppCompatActivity(), View.OnClickListener {

    val ids = arrayOf(
        intArrayOf(R.id.button00, R.id.button01, R.id.button02, R.id.button03, R.id.button04, R.id.button05),
        intArrayOf(R.id.button10, R.id.button11, R.id.button12, R.id.button13, R.id.button14, R.id.button15),
        intArrayOf(R.id.button20, R.id.button21, R.id.button22, R.id.button23, R.id.button24, R.id.button25),
        intArrayOf(R.id.button30, R.id.button31, R.id.button32, R.id.button33, R.id.button34, R.id.button35),
        intArrayOf(R.id.button40, R.id.button41, R.id.button42, R.id.button43, R.id.button44, R.id.button45),
        intArrayOf(R.id.button50, R.id.button51, R.id.button52, R.id.button53, R.id.button54, R.id.button55)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)
        registerListeners()
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
        val partida = Partida(TableroConecta4(tablero), jugadores)
        partida.addObservador(ObservadorConecta4())
        partida.comenzar()

    }

    override fun onClick(view: View) {
        if (view is ImageButton) {
            view.setImageResource(R.drawable.ic_brightness_1_black_24dp)
        }
    }

    private fun registerListeners() {
        var button: ImageButton
        for (i in 0 until ids.size) for (j in 0 until ids.size) {
            button = findViewById(ids[i][j])
            button.setOnClickListener(this)
        }
    }

    private fun fromViewToColumna(view: View): Int {
        for (ficha in 0 until ids.size)
            for (columna in 0 until ids.size)
                if (view.id == ids[ficha][columna])
                    return columna
        return -1
    }
}
