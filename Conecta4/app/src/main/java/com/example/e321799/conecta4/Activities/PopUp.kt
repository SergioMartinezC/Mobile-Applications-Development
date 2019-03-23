package com.example.e321799.conecta4.Activities

import android.app.Activity
import android.hardware.display.DisplayManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TableRow
import com.example.e321799.conecta4.R

class PopUp : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val MENU_PARTIDA = 0
        val MENU_PARTIDA_FINALIZADA = 1
        val MENU_FIN_PARTIDA = 2

        super.onCreate(savedInstanceState)

        val extras = intent.extras.getInt("menu")

        if (extras == MENU_FIN_PARTIDA) {
            setContentView(R.layout.activity_end_game_pop_up)
        } else {
            setContentView(R.layout.activity_menu_board)
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
}
