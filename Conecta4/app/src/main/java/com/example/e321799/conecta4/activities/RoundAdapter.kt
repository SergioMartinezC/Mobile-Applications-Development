package com.example.e321799.conecta4.activities

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.e321799.conecta4.model.Round
import com.example.e321799.conecta4.R
import com.example.e321799.conecta4.views.ERView

/**
 * Mantiene una lista de referencias a los elementos de un ítem de la lista
 */
class RoundHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var idTextView: TextView
    var boardTextView: ERView
    var dateTextView: TextView
    private val item : RelativeLayout
    private lateinit var round: Round

    init {
        idTextView = itemView.findViewById(R.id.list_item_id) as TextView
        dateTextView = itemView.findViewById(R.id.list_item_date) as TextView
        boardTextView = itemView.findViewById(R.id.list_item_board) as ERView
        item = itemView.findViewById(R.id.itemView) as RelativeLayout

    }

    /**
     * Asigna la funcionalidad para hacer clickables los elementos de la vista
     */
    fun bindRound(round: Round, listener: (Round) -> Unit) {
        this.round = round
        idTextView.text = round.title
        dateTextView.text = round.date.substring(0,19)
        boardTextView.setBoard(round.board)
        boardTextView.invalidate()
        item.setOnClickListener{ listener(round)}
        boardTextView.setOnClickListener {listener(round)}
        idTextView.setOnClickListener { listener(round) }
        dateTextView.setOnClickListener { listener(round) }

    }
}

/**
 *
 */
class RoundAdapter(var rounds: List<Round>, val listener: (Round) -> Unit): RecyclerView.Adapter<RoundHolder>() {
    /**
    * Infla una vista a partir del fichero de diseño de los ítems y crea un nuevo RoundHolder con esta vista como argumento
    **/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoundHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_round, parent, false)
        return RoundHolder(view)
    }

    /**
     * Devuelve el tamaño de la lista de rondas
     */
    override fun getItemCount(): Int = rounds.size

    /**
     * reutiliza un RoundHolder ya existente para mostrar los datos de otra partida
     */
    override fun onBindViewHolder(holder: RoundHolder, position: Int) {
        holder.bindRound(rounds[position], listener)
    }
}