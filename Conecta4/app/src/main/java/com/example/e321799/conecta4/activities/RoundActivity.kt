package com.example.e321799.conecta4.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.e321799.conecta4.R
import android.content.Context
import android.content.Intent
import com.example.e321799.conecta4.model.Round

/**
 * Actividad que se lanza cuando se muestra una partida
 */
class RoundActivity : AppCompatActivity(), RoundFragment.OnRoundFragmentInteractionListener {
    /**
     * Funcion que se ejecuta una vez que se crea la actividad
     */
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

    /**
     * Funcion que se ejecuta cada vez que se actualiza el tablero
     */
    override fun onRoundUpdated(round: Round) {    }

    /**
     * Metodo de factoria que genera un nuevo intent lanzando RoundActivity
     */
    companion object {
        val EXTRA_ROUND_ID = "es.uam.eps.dadm.er10.round_id"
        fun newIntent(packageContext: Context, roundId: String): Intent {
            val intent = Intent(packageContext, RoundActivity::class.java)
            intent.putExtra(EXTRA_ROUND_ID, roundId)
            return intent
        }
    }
}
