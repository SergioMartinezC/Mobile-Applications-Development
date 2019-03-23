package com.example.e321799.conecta4.Activities

import android.widget.ImageButton
import TableroConecta4
import JugadorConecta4Humano
import com.example.e321799.conecta4.Model.JugadorConecta4CPU
import com.example.e321799.conecta4.R
import es.uam.eps.multij.Jugador
import es.uam.eps.multij.Partida

fun ImageButton.update(tablero: TableroConecta4, i: Int, j: Int, game: Partida) {
    var jugador: Jugador
    when (tablero.tablero[i][j]) {
        tablero.JUGADOR_1 -> {
            jugador = game.getJugador(tablero.JUGADOR_1-1)
            if (jugador is JugadorConecta4Humano) {
                setImageResource(jugador.drawable)
            }
            else if (jugador is JugadorConecta4CPU) {
                setImageResource(jugador.drawable)
            }
        }
        tablero.JUGADOR_2 ->  {
            jugador = game.getJugador(tablero.JUGADOR_2-1)
            if (jugador is JugadorConecta4Humano) {
                setImageResource(jugador.drawable)
            }
            else if (jugador is JugadorConecta4CPU) {
                setImageResource(jugador.drawable)
            }
        }
        tablero.CASILLA_VACIA -> setImageResource(R.drawable.token_white_48dp)
    }
}