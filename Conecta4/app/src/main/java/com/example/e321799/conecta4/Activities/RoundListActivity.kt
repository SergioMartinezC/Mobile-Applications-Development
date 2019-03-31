package com.example.e321799.conecta4.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.example.e321799.conecta4.Model.Round
import com.example.e321799.conecta4.Model.RoundRepository
import com.example.e321799.conecta4.R
import kotlinx.android.synthetic.main.activity_twopane.*
import kotlinx.android.synthetic.main.fragment_round_list.*

/**
 * Clase que representa la actividad que muestra la lista de partidas
 */
class RoundListActivity : AppCompatActivity(),
    RoundListFragment.OnRoundListFragmentInteractionListener,
    RoundFragment.OnRoundFragmentInteractionListener {

    lateinit var round : Round
    override fun onRoundSelected(round: Round) {
        this.round = round
        val fm = supportFragmentManager
        if (detail_fragment_container == null) {
            startActivity(RoundActivity.newIntent(this, round.id))
        } else {
            fm.executeTransaction { replace(R.id.detail_fragment_container,
                RoundFragment.newInstance(round.id)) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_master_detail)
        val fm = supportFragmentManager
        if (fm.findFragmentById(R.id.fragment_container) == null) {
            fm.executeTransaction { add(R.id.fragment_container, RoundListFragment())}
        }

        var tb = findViewById<android.support.v7.widget.Toolbar>(R.id.my_toolbar)
        setSupportActionBar(tb)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * Funcion que es llamada cuando una partida se va a añadir a la lista
     */
    override fun onRoundAdded() {
        RoundRepository.addRound()
    }

    /**
     * Funcion que se llama para crear el menu de opciones utilizando
     * [menu]
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    /**
     * Funcion que se llama cuando se actualiza una partida
     */
    override fun onRoundUpdated() {
        round_recycler_view.adapter?.notifyDataSetChanged()
    }

    /**
     *
     */
    override fun onResume() {
        super.onResume()
        onRoundUpdated()
    }

}
