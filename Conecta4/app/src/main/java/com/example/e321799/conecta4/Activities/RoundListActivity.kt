package com.example.e321799.conecta4.Activities

<<<<<<< HEAD
import android.content.Intent
=======
>>>>>>> 1a08e1a8282dd472e11e4cfc5e58d80308ed1770
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.example.e321799.conecta4.Model.Round
import com.example.e321799.conecta4.R
import com.example.e321799.conecta4.Model.RoundRepository
import kotlinx.android.synthetic.main.activity_round_list.*

class RoundListActivity: AppCompatActivity() {
    fun onRoundSelected(round: Round) {
<<<<<<< HEAD
        val intent = Intent(this, BoardActivity::class.java)
        intent.putExtra("round", round)
        startActivity(intent)
=======
        TODO()
>>>>>>> 1a08e1a8282dd472e11e4cfc5e58d80308ed1770
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_list)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            itemAnimator = DefaultItemAnimator()
        }
    }
    override fun onResume() {
        super.onResume()
        updateUI()
    }
    fun updateUI() {
        recyclerView.apply {
            if (adapter == null) {
                adapter = RoundAdapter(RoundRepository.rounds)
                    { round -> onRoundSelected(round)  }
            }
            else {
                adapter?.notifyDataSetChanged()
            }
        }
    }
}