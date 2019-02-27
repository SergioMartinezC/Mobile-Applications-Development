package com.example.e321799.conecta4

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {

    val ids = arrayOf(intArrayOf(R.id.button00, R.id.button01, R.id.button02, R.id.button03, R.id.button04, R.id.button05),
                intArrayOf(R.id.button10, R.id.button11, R.id.button12, R.id.button13, R.id.button14, R.id.button15),
                intArrayOf(R.id.button20, R.id.button21, R.id.button22, R.id.button23, R.id.button24, R.id.button25),
                intArrayOf(R.id.button30, R.id.button31, R.id.button32, R.id.button33, R.id.button34, R.id.button35),
                intArrayOf(R.id.button40, R.id.button41, R.id.button42, R.id.button43, R.id.button44, R.id.button45),
                intArrayOf(R.id.button50, R.id.button51, R.id.button52, R.id.button53, R.id.button54, R.id.button55))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)
    }

    fun onClickButton(view: View) {
        if (view is ImageButton) {
            view.setImageResource(R.drawable.ic_brightness_1_black_24dp)
        }
    }
}
