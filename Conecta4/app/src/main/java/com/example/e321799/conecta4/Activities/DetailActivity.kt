package com.example.e321799.conecta4.Activities


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.e321799.conecta4.R
import com.example.e321799.conecta4.TokenSelectionPVE

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pregame_fragment)
        val message = intent.getStringExtra(EXTRA_ARGUMENT)
        val fm = supportFragmentManager
        if (fm.findFragmentById(R.id.menu_container) == null) {
            val fragment = TokenSelectionPVE.newInstance(message)
            fm.beginTransaction().add(R.id.menu_container, fragment).commit()
        }
    }

    companion object  {
        val EXTRA_ARGUMENT = "com.example.e321799.conecta4.Activities.extra"
        fun newIntent(package_content: Context, message: String) : Intent {
            val intent = Intent(package_content, DetailActivity::class.java)
            intent.putExtra(EXTRA_ARGUMENT, message)
            return intent
        }
    }
}