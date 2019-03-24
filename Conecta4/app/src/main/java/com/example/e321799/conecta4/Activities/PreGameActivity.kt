package com.example.e321799.conecta4.Activities

import android.content.Intent
import android.media.session.MediaSession
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageButton
import com.example.e321799.conecta4.PreGameMenuFragment
import com.example.e321799.conecta4.R
import com.example.e321799.conecta4.TokenSelection
import kotlinx.android.synthetic.main.activity_pregame_view.*
import kotlinx.android.synthetic.main.fragment_pre_game_menu.*

val drawableIds = intArrayOf(
    R.drawable.token_blue_48dp,
    R.drawable.token_green_48dp,
    R.drawable.token_pink_48dp,
    R.drawable.token_red_48dp,
    R.drawable.token_yellow_48dp
)

class PreGameActivity : AppCompatActivity(), View.OnClickListener, PreGameMenuFragment.OnFragmentInteractionListener {

    var ficha_jugador_1 = 0
    var ficha_jugador_2 = 0

    private val buttonTokenIds = arrayOf(
        R.id.blue_token,
        R.id.green_token,
        R.id.pink_token,
        R.id.red_token,
        R.id.yellow_token
    )

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pregame_view)
        setTags()
        registerListeners()
    }

    private fun setTags() {
        for (i in 0 until drawableIds.size) {
            var button : ImageButton = findViewById(buttonTokenIds[i])
            button.tag = drawableIds[i]
        }
    }

    private fun registerListeners() {
        for (i in 0 until buttonTokenIds.size) {
            var button : ImageButton = findViewById(buttonTokenIds[i])
            button.setOnClickListener(this)
        }
        button_start_game.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v?.id == R.id.button_start_game) {
            if (ficha_jugador_1 != 0 && ficha_jugador_2 != 0) {
                val intent = Intent(this, BoardActivity::class.java)
                intent.putExtra("ficha_jugador_1", ficha_jugador_1)
                intent.putExtra("ficha_jugador_2", ficha_jugador_2)
                startActivity(intent)
            } else {
                // todo
            }

        }
        else {
            if (ficha_jugador_1 == 0) {
                ficha_jugador_1 = findViewById<ImageButton>(v!!.id).tag.toString().toInt()
            } else {
                ficha_jugador_2 = findViewById<ImageButton>(v!!.id).tag.toString().toInt()
            }
        }
    }

    override fun onFragmentInteraction(uri: Uri) {
        val fm = supportFragmentManager
        if (token_selection != null) {
            val token_fragment = TokenSelection.newInstance("Marmol")
        }
    }

}