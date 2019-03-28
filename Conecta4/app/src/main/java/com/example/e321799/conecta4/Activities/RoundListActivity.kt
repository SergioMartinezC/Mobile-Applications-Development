package com.example.e321799.conecta4.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.example.e321799.conecta4.Model.Round
import com.example.e321799.conecta4.Model.RoundRepository
import com.example.e321799.conecta4.R
import com.example.e321799.conecta4.R.id.detail_fragment_container
import kotlinx.android.synthetic.main.activity_round_list.*

class RoundListActivity : AppCompatActivity(),
    RoundListFragment.OnRoundListFragmentInteractionListener {

    override fun onRoundSelected(round: Round) {
        val fm = supportFragmentManager
        if (detail_fragment_container == null) {
            startActivity(RoundActivity.newIntent(this, round.id))
        } else {
            fm.executeTransaction { replace(R.id.detail_fragment_container,
                RoundFragment.newInstance(round.id)) }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_master_detail)
        val fm = supportFragmentManager
        if (fm.findFragmentById(R.id.fragment_container) == null) {
            fm.executeTransaction { add(R.id.fragment_container, RoundListFragment())}
        }
    }

}
