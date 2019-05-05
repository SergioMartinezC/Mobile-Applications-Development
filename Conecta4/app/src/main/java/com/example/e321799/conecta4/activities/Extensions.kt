package com.example.e321799.conecta4.activities

import TableroConecta4
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.RecyclerView
import com.example.e321799.conecta4.model.Round
import com.example.e321799.conecta4.model.RoundRepository
import com.example.e321799.conecta4.R
import android.content.Context
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import com.example.e321799.conecta4.model.RoundRepositoryFactory



/**
 * @brief Funcion que extiende de RecyclerView y actualiza la lista de partidas guardadas
 * @param userName nombre del usuario
 * @param onClickListener listener
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
    /*if (repository is DataBase) {*/
        repository?.getRounds(userName, "", "", roundsCallback)
   /* }
    else if(repository is FBDataBase) {
        repository?.startListeningChanges(roundsCallback)
    }*/
}

/**
 * @brief Funcion que extiende de FragmentManager y ejecuta una transicion sobre un fragmento
 * con unas operaciones dadas
 * @param operations operaciones a realizar
 */
fun FragmentManager.executeTransaction(operations: (FragmentTransaction.() -> Unit)) {
    val transaction = beginTransaction()
    transaction.operations()
    transaction.commit()
}


/**
 * @brief Funcion que extiende de Paint y asigna un color en función de qué ficha es cada jugador
 * @param board tablero
 * @param i fila
 * @param j columna
 * @param context contexto
 */
fun Paint.setColor(board: TableroConecta4, i: Int, j: Int, context: Context) {
    color = when(board.tablero[i][j]) {
        board.JUGADOR_1 -> ContextCompat.getColor(context, R.color.blue_token)
        board.JUGADOR_2 -> ContextCompat.getColor(context, R.color.red_token)
        else -> ContextCompat.getColor(context, R.color.colorPrimary)
    }
}