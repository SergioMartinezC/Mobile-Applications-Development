package com.example.e321799.conecta4.activities


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.example.e321799.conecta4.model.Round
import com.example.e321799.conecta4.R
import kotlinx.android.synthetic.main.fragment_round_list.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * Fragmento que representa la lista de partidas.
 *
 */
class RoundListFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    var listener: OnRoundListFragmentInteractionListener? = null
    interface OnRoundListFragmentInteractionListener {
        fun onRoundSelected(round: Round)
        fun onRoundAdded()
        fun onPreferenceSelected()
    }

    /**
     *
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_round_list, container, false)
    }

    /**
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    /**
     *
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu_item_new_round -> {
                listener?.onRoundAdded()
                return true
            }
            R.id.menu_item_settings -> {
                listener?.onPreferenceSelected()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    /**
     *
     */
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnRoundListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() +
                    " must implement OnRoundListFragmentInteractionListener")
        }
    }

    /**
     *
     */
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onResume() {
        super.onResume()
        round_recycler_view.update(SettingsActivity.getPlayerUUID(context!!))
        { round -> listener?.onRoundSelected(round) }
    }

    /**
     *
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.round_recycler_view)
        super.onViewCreated(view, savedInstanceState)
        round_recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            update(SettingsActivity.getPlayerUUID(context!!))
            { round -> listener?.onRoundSelected(round) }
        }
    }
}
