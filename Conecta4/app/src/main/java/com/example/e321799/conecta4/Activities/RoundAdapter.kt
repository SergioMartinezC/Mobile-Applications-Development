package com.example.e321799.conecta4.Activities

import android.support.v7.view.menu.MenuView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.e321799.conecta4.Model.Round
import com.example.e321799.conecta4.R
import com.example.e321799.conecta4.views.ERView

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

class RoundAdapter(val rounds: List<Round>, val listener: (Round) -> Unit): RecyclerView.Adapter<RoundHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoundHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_round, parent, false)
        return RoundHolder(view)
    }
    override fun getItemCount(): Int = rounds.size
    override fun onBindViewHolder(holder: RoundHolder, position: Int) {
        holder.bindRound(rounds[position], listener)
    }
}