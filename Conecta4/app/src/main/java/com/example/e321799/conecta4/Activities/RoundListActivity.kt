package com.example.e321799.conecta4.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.example.e321799.conecta4.Model.Round
import com.example.e321799.conecta4.Model.RoundRepository
import com.example.e321799.conecta4.R
import kotlinx.android.synthetic.main.activity_round_list.*

class RoundListActivity : AppCompatActivity() {
    fun onRoundSelected(round: Round) {
        val intent = RoundActivity.newIntent(this, round.id)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_list)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            itemAnimator = DefaultItemAnimator()
            update { round -> onRoundSelected(round)}
        }
    }
    override fun onResume() {
        super.onResume()
        recyclerView.update { round -> onRoundSelected(round) }
    }

}
