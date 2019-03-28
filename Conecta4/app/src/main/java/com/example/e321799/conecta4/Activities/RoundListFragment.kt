package com.example.e321799.conecta4.Activities


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.e321799.conecta4.Model.Round
import com.example.e321799.conecta4.R
import kotlinx.android.synthetic.main.activity_round_list.*
import kotlinx.android.synthetic.main.fragment_round_list.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class RoundListFragment : Fragment() {

    fun onRoundSelected(round: Round) {
        val intent = RoundActivity.newIntent(context!!, round.id)
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_round_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        round_recycler_view.apply {
            /*  round_recycler_view */
            layoutManager = LinearLayoutManager(activity)
            itemAnimator = DefaultItemAnimator()
            update { round -> onRoundSelected(round) }
        }
    }
}
