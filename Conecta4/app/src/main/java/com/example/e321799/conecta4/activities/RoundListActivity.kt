package com.example.e321799.conecta4.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.view.Menu
import com.example.e321799.conecta4.model.Round
import com.example.e321799.conecta4.model.RoundRepository
import com.example.e321799.conecta4.R
import com.example.e321799.conecta4.model.RoundRepositoryFactory
import kotlinx.android.synthetic.main.activity_twopane.*
import kotlinx.android.synthetic.main.fragment_round_list.*

/**
 * Clase que representa la actividad que muestra la lista de partidas
 */
class RoundListActivity : AppCompatActivity(),
    RoundListFragment.OnRoundListFragmentInteractionListener,
    RoundFragment.OnRoundFragmentInteractionListener {


    override fun onRoundSelected(round: Round) {
        if (detail_fragment_container == null) {
            if (round.board.comprobarGanador() == 0) {
                startActivity(RoundActivity.newIntent(this, round.toJSONString()))
            }
            else {
                Snackbar.make(findViewById(R.id.round_recycler_view),
                    R.string.round_already_finished, Snackbar.LENGTH_LONG).show()
            }
        } else {
            if (round.board.comprobarGanador() == 0) {
                supportFragmentManager.executeTransaction {
                    replace(
                        R.id.detail_fragment_container,
                        RoundFragment.newInstance(round.toJSONString())
                    )
                }
            }
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
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false)

    }

    /**
     * Funcion que es llamada cuando una partida se va a añadir a la lista
     */
    override fun onRoundAdded() {
        val round = Round(/* SettingsActivity.getBoardSize(this).toInt() */)
        round.firstPlayerName = "OPEN_ROUND"
        round.firstPlayerUUID = "Random"
        round.secondPlayerName = SettingsActivity.getPlayerName(this)
        round.secondPlayerUUID = SettingsActivity.getPlayerUUID(this)
        val repository = RoundRepositoryFactory.createRepository(this)
        val callback = object : RoundRepository.BooleanCallback {
            override fun onResponse(response: Boolean) {
                if (response == false)
                    Snackbar.make(findViewById(R.id.round_recycler_view),
                        R.string.error_adding_round, Snackbar.LENGTH_LONG).show()
                else {
                    Snackbar.make(findViewById(R.id.round_recycler_view),
                        "New " + round.title + " added", Snackbar.LENGTH_LONG).show()
                    val fragmentManager = supportFragmentManager
                    val roundListFragment =
                        fragmentManager.findFragmentById(R.id.fragment_container)
                                as RoundListFragment
                    roundListFragment.recyclerView.update(
                        SettingsActivity.getPlayerUUID(baseContext),
                        { round -> onRoundSelected(round) }
                    )
                }
            }
        }
        repository?.addRound(round, callback)
    }

    /**
     * Funcion que es llamada cuando se accede a las opciones
     */
    override fun onPreferenceSelected() {
        startActivity(Intent(this, SettingsActivity::class.java))
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
    override fun onRoundUpdated(round: Round) {
        val repository = RoundRepositoryFactory.createRepository(this)
        val callback = object : RoundRepository.BooleanCallback {
            override fun onResponse(response: Boolean) {
                if (response == true) {
                    round_recycler_view.update(
                        SettingsActivity.getPlayerUUID(baseContext),
                        { round -> onRoundSelected(round) }
                    )
                } else
                    Snackbar.make(findViewById(R.id.title),
                        R.string.error_updating_round,
                        Snackbar.LENGTH_LONG).show()
            }
        }
        repository?.updateRound(round, callback)
    }

    /**
     *
     */
   override fun onResume() {
        super.onResume()
        round_recycler_view.update(SettingsActivity.getPlayerUUID(baseContext), {round -> onRoundSelected(round)})
    }

}
