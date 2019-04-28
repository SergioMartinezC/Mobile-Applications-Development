package com.example.e321799.conecta4.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.example.e321799.conecta4.model.RoundRepository
import com.example.e321799.conecta4.R
import com.example.e321799.conecta4.model.Round
import com.example.e321799.conecta4.model.RoundRepositoryFactory
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Actividad principal de la aplicacion que lanza el menú principal
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {
    var tupack = 0
    /**
     * Funcion que se ejecuta al crear la actividad
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button_play.setOnClickListener(this)
        button_load_game.setOnClickListener(this)
        button_exit.setOnClickListener(this)
    }

    /**
     * Definimos qué acciones hace cada boton del menu principal
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_play -> {
                val round = Round(/* SettingsActivity.getBoardSize(this).toInt() */)
                round.firstPlayerName = "Random"
                round.firstPlayerUUID = "Random"
                round.secondPlayerName = SettingsActivity.getPlayerName(this)
                round.secondPlayerUUID = SettingsActivity.getPlayerUUID(this)
                val repository = RoundRepositoryFactory.createRepository(this)
                val callback = object : RoundRepository.BooleanCallback {
                    override fun onResponse(response: Boolean) {
                        if (response == false)
                            Snackbar.make(v,
                                R.string.error_adding_round, Snackbar.LENGTH_LONG).show()
                        else {
                            Snackbar.make(v,
                                "New " + round.title + " added", Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
                repository?.addRound(round, callback)
                startActivity(RoundActivity.newIntent(this, round.toJSONString()))
            }
            R.id.button_load_game -> {
                val intent = Intent(this, RoundListActivity::class.java)
                startActivity(intent)
            }
            R.id.button_exit -> {
                System.exit(0)
            }
        }
    }
}
