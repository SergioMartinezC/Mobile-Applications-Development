package com.example.e321799.conecta4.Activities

import android.widget.ImageButton
import TableroConecta4
import JugadorConecta4Humano
import com.example.e321799.conecta4.R

fun ImageButton.update(tablero: TableroConecta4, i: Int, j: Int) {
    when (tablero.tablero[i][j]) {
        tablero.JUGADOR_1 -> setImageResource(R.drawable.token_blue_48dp)
        tablero.JUGADOR_2 -> setImageResource(R.drawable.token_red_48dp)
        tablero.CASILLA_VACIA -> setImageResource(R.drawable.token_white_48dp)
    }
}