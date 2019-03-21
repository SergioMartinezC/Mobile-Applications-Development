package com.example.e321799.conecta4.Activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageButton
import com.example.e321799.conecta4.R
import kotlinx.android.synthetic.main.activity_pregame_view.*

class PreGameActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pregame_view)
        blue_token.setOnClickListener(this)
        blue_token.tag = R.drawable.token_blue_48dp
        green_token.setOnClickListener(this)
        green_token.tag = R.drawable.token_green_48dp
        pink_token.setOnClickListener(this)
        pink_token.tag = R.drawable.token_pink_48dp

    }

    override fun onClick(v: View?) {
        val buttonPressed = findViewById<ImageButton>(v!!.id)
        val intent = Intent(this, BoardActivity::class.java)
        var tag = buttonPressed.tag.toString().toInt()
        intent.putExtra("drawable",tag )
        startActivity(intent)
    }

}