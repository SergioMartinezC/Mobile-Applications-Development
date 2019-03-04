package com.example.e321799.conecta4.Activities

import android.widget.ImageButton
import TableroConecta4


import com.example.e321799.conecta4.R

fun ImageButton.update(tablero: TableroConecta4, i: Int, j: Int) {
    if (tablero.tablero[i][j] == tablero.JUGADOR_1.toInt()) {
        setImageResource(R.drawable.ic_brightness_1_black_24dp)
    } else if (tablero.tablero[i][j] == tablero.JUGADOR_1.toInt()) {
        setImageResource(R.drawable.ic_brightness_1_black_24dp)
    } else {
        setImageResource(R.drawable.ic_brightness_1_black_24dp)
    }
}