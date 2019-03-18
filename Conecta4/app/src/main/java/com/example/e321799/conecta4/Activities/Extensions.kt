package com.example.e321799.conecta4.Activities

import android.widget.ImageButton
import TableroConecta4


import com.example.e321799.conecta4.R

fun ImageButton.update(tablero: TableroConecta4, i: Int, j: Int) {
    if (tablero.tablero[i][j] == tablero.JUGADOR_1) {
        setImageResource(R.drawable.token_blue_48dp)
    } else if (tablero.tablero[i][j] == tablero.JUGADOR_2) {
        setImageResource(R.drawable.token_red_48dp)
    } else {
        setImageResource(R.drawable.token_white_48dp)
    }
}