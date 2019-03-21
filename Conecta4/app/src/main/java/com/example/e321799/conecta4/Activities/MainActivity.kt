package com.example.e321799.conecta4.Activities

import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.app.AppCompatActivity
import com.example.e321799.conecta4.R
import android.content.Intent
import android.view.View
import android.os.Bundle


class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button_play.setOnClickListener(this)
        button_exit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.button_play-> {
                val intent = Intent(this, PreGameActivity::class.java)
                startActivity(intent)
            }
            R.id.button_exit-> {
                System.exit(0)
            }
        }

    }


}
