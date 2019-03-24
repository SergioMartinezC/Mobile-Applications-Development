package com.example.e321799.conecta4.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.TableRow
import com.example.e321799.conecta4.R
import kotlinx.android.synthetic.main.activity_menu_board.*

class PopUp : Activity(), View.OnClickListener {

    var ficha1 = 0
    var ficha2 = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        val MENU_PARTIDA = 0
        val MENU_PARTIDA_FINALIZADA = 1
        val MENU_FIN_PARTIDA = 2

        super.onCreate(savedInstanceState)
        ficha1 = intent.extras.getInt("ficha_jugador_1")
        ficha2 = intent.extras.getInt("ficha_jugador_2")
        val extras = intent.extras.getInt("menu")

        if (extras == MENU_FIN_PARTIDA) {
            setContentView(R.layout.activity_end_game_pop_up)
        } else {

            setContentView(R.layout.activity_menu_board)

            load_game.setOnClickListener(this)
            new_game.setOnClickListener(this)
            main_menu.setOnClickListener(this)
            button_back.setOnClickListener(this)
            save_game.setOnClickListener(this)

            if (extras == MENU_PARTIDA) {
                findViewById<TableRow>(R.id.load_row).visibility = View.GONE
            } else if (extras == MENU_PARTIDA_FINALIZADA) {
                findViewById<TableRow>(R.id.save_row).visibility = View.GONE
            }
        }

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width = dm.widthPixels
        val height = dm.heightPixels


        window.setLayout(width * 100 / 150, height * 100 / 170)

        val params : WindowManager.LayoutParams = window.attributes
        params.gravity = Gravity.CENTER
        params.x = 0
        params.y = -20

        window.attributes = params
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.save_game-> {

            }
            R.id.load_game-> {

            }
            R.id.new_game-> {
                val intent = Intent(this, BoardActivity::class.java)
                intent.putExtra("ficha_jugador_1", ficha1)
                intent.putExtra("ficha_jugador_2", ficha2)
                startActivity(intent)
            }
            R.id.main_menu-> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.button_back-> {
                this.finish()
            }
        }
    }
}
