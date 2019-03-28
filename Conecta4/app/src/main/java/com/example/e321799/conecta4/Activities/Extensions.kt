package com.example.e321799.conecta4.Activities

import android.widget.ImageButton
import TableroConecta4
import android.support.v7.widget.RecyclerView
import com.example.e321799.conecta4.Model.Round
import com.example.e321799.conecta4.Model.RoundRepository
import com.example.e321799.conecta4.R

fun ImageButton.update(board: TableroConecta4, i: Int, j: Int) {
    if (board.tablero[i][j] == board.JUGADOR_1) {
        setImageResource(R.drawable.token_blue_48dp)
    }
    else if (board.tablero[i][j] == board.CASILLA_VACIA) {
        setImageResource(R.drawable.token_white_48dp)
    }
    else {
        setImageResource(R.drawable.token_red_48dp)
    }
}

fun RecyclerView.update(onClickListener: (Round) -> Unit) {
    if (adapter == null)
        adapter = RoundAdapter(RoundRepository.rounds, onClickListener)
    else
        adapter?.notifyDataSetChanged()
}