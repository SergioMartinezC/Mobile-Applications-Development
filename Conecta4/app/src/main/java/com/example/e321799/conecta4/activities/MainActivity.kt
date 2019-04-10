package com.example.e321799.conecta4.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.e321799.conecta4.model.RoundRepository
import com.example.e321799.conecta4.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Actividad principal de la aplicacion que lanza el menú principal
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {

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
                val intent = RoundActivity.newIntent(this, RoundRepository.addRound())
                startActivity(intent)
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
