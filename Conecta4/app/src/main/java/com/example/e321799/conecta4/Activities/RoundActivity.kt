package com.example.e321799.conecta4.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.e321799.conecta4.R
import TableroConecta4
import JugadorConecta4Humano
import android.content.Context
import android.content.Intent
import android.support.v4.app.FragmentContainer
import android.widget.Toolbar
import com.example.e321799.conecta4.Model.Round
import com.example.e321799.conecta4.Model.RoundRepository
import es.uam.eps.multij.*
import kotlinx.android.synthetic.main.activity_fragment.*
import kotlinx.android.synthetic.main.activity_round.*
import kotlinx.android.synthetic.main.activity_twopane.*

class RoundActivity : AppCompatActivity(), RoundFragment.OnRoundFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        val fm = supportFragmentManager
        if (fm.findFragmentById(R.id.fragment_container) == null) {
            val fragment = RoundFragment.newInstance(intent.getStringExtra(EXTRA_ROUND_ID))
            fm.executeTransaction { add(R.id.fragment_container, fragment) }
        }

        var tb = findViewById<android.support.v7.widget.Toolbar>(R.id.my_toolbar)
        setSupportActionBar(tb)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onRoundUpdated() {    }

    companion object {
        val EXTRA_ROUND_ID = "es.uam.eps.dadm.er10.round_id"
        fun newIntent(packageContext: Context, roundId: String): Intent {
            val intent = Intent(packageContext, RoundActivity::class.java)
            intent.putExtra(EXTRA_ROUND_ID, roundId)
            return intent
        }
    }
}
