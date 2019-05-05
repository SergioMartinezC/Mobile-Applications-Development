package com.example.e321799.conecta4.activities


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.example.e321799.conecta4.model.Round
import com.example.e321799.conecta4.R
import com.example.e321799.conecta4.firebase.FBDataBase
import com.example.e321799.conecta4.model.RoundRepository
import com.example.e321799.conecta4.model.RoundRepositoryFactory
import kotlinx.android.synthetic.main.fragment_round_list.*



/**
 * @brief Fragmento que representa la lista de partidas.
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
     * @brief Funcion que se ejecuta cuando se crea la vista
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_round_list, container, false)
    }

    /**
     * @brief Funcion que se ejecuta cuand se crea la instancia
     * @param savedInstanceState estado de la instanca guardado
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    /**
     * @brief Funcion que se ejecuta al seleccionar una opcion del menu
     * @param item opcion
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
     * @brief Funcion que se ejecuta al vincular el contexto
     * @param context contexto
     */
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        val repository = RoundRepositoryFactory.createRepository(context!!)
        if (repository is FBDataBase){
            val roundsCallback = object : RoundRepository.RoundsCallback {
                override fun onResponse(rounds: List<Round>) {
                    recyclerView.update(SettingsActivity.getPlayerUUID(context)
                    ) { round -> listener?.onRoundSelected(round) }
                }
                override fun onError(error: String) {
                }
            }
            repository.startListeningChanges(roundsCallback)
        }
        if (context is OnRoundListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() +
                    " must implement OnRoundListFragmentInteractionListener")
        }
    }

    /**
     * @brief Funcion que se ejecuta al desvincular el contexto
     */
    override fun onDetach() {
        super.onDetach()
        println("-----------------------Detach")
        listener = null
    }

    /**
     * @brief Funcion que se ejecuta al continuar con la instancia
     */
    override fun onResume() {
        super.onResume()
        round_recycler_view.update(SettingsActivity.getPlayerUUID(context!!))
        { round -> listener?.onRoundSelected(round) }
    }

    /**
     * @brief Funcion que se ejecuta al crearse la vista
     * @param view vista
     * @param savedInstanceState estado de la instancia guardada
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        println("-----------------------VIEWCREATED")
        recyclerView = view.findViewById(R.id.round_recycler_view)
        super.onViewCreated(view, savedInstanceState)
        round_recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            update(SettingsActivity.getPlayerUUID(context!!))
            { round -> listener?.onRoundSelected(round) }
        }
    }
}
