package com.example.e321799.conecta4


import android.media.session.MediaSession
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_token_selection_pve.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "com.example.e321799.conecta4.param1"

/**
 * A simple [Fragment] subclass.
 * Use the [TokenSelectionPVE.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TokenSelectionPVE : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_token_selection_pve, container, false)
    }

    override fun onStart() {
        super.onStart()
        textView.text = param1
    }

    companion object {
        fun newInstance(param1: String): TokenSelectionPVE {
            val fragment = TokenSelectionPVE()
            fragment.arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
            }
            return fragment
        }
    }
}
