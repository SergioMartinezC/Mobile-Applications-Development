package com.example.e321799.conecta4.Activities

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.example.e321799.conecta4.PregameMenuFragment
import com.example.e321799.conecta4.R
import com.example.e321799.conecta4.TokenSelectionPVE
import kotlinx.android.synthetic.main.pregame_fragment.*


class PreGameActivity : AppCompatActivity(), PregameMenuFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(string: String) {
        val fm = supportFragmentManager
        if (token_selection_container != null) {
            val tokenSelection = TokenSelectionPVE.newInstance("Button Pressed")
            fm.beginTransaction().add(R.id.token_selection_container, tokenSelection).commit()
        }
        else {
            val intent = DetailActivity.newIntent(this,"EJEMPLO")
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pregame_fragment)
        val fm = supportFragmentManager
        val fragment = PregameMenuFragment()
        fm.beginTransaction().add(R.id.menu_container, fragment).commit()
    }
}