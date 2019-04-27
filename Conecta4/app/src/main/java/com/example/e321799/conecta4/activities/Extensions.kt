package com.example.e321799.conecta4.activities

import android.widget.ImageButton
import TableroConecta4
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.e321799.conecta4.model.Round
import com.example.e321799.conecta4.model.RoundRepository
import com.example.e321799.conecta4.R
import android.content.Context
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import com.example.e321799.conecta4.model.RoundRepositoryFactory
import com.example.e321799.conecta4.views.ERButton

/**
 * Funcion que extiende ImageButton y actualiza una ficha del [board] en la posicion [i] [j]
 */
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

/**
 * Funcion que extiende de RecyclerView y actualiza la lista de partidas guardadas
 */
fun RecyclerView.update(userName: String, onClickListener: (Round) -> Unit) {
    val repository = RoundRepositoryFactory.createRepository(context)
    val roundsCallback = object : RoundRepository.RoundsCallback {
        override fun onResponse(rounds: List<Round>) {
            if (adapter == null)
                adapter = RoundAdapter(rounds, onClickListener)
            else {
                (adapter as RoundAdapter).rounds = rounds
                adapter?.notifyDataSetChanged()
            }
        }
        override fun onError(error: String) {
        }
    }
    repository?.getRounds(userName, "", "", roundsCallback)
}

/**
 * Funcion que extiende de FragmentManager y ejecuta una transicion sobre un fragmento
 * con unas operaciones dadas [operations]
 */
fun FragmentManager.executeTransaction(operations: (FragmentTransaction.() -> Unit)) {
    val transaction = beginTransaction()
    transaction.operations()
    transaction.commit()
}

/**
 * Funcion que extiende de la clase View y actualiza el tablero usando la ronda actual
 */
fun View.update(round: Round) {
    val ids = arrayOf(
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
    for (i in 0 until ids.size) {
        for (j in 0 until ids[i].size) {
            val button = findViewById<ERButton>(ids[i][j])
            with(button) {
                if (round.board.tablero[i][j] == round.board.JUGADOR_1)
                    button.human()
                else if (round.board.tablero[i][j] == round.board.JUGADOR_2)
                    button.randomPlayer()
                else
                    button.notClicked()
            }
        }
    }
}

/**
 * Funcion que extiende de Paint y asigna un color en función de qué ficha es cada jugador
 */
fun Paint.setColor(board: TableroConecta4, i: Int, j: Int, context: Context) {
    if (board.tablero[i][j] === board.JUGADOR_1)
        setColor(ContextCompat.getColor(context, R.color.blue_token))
    else if (board.tablero[i][j] === board.JUGADOR_2)
        setColor(ContextCompat.getColor(context, R.color.red_token))
    else
        setColor(ContextCompat.getColor(context, R.color.colorPrimary))
}