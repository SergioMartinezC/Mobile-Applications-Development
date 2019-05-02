package com.example.e321799.conecta4.activities

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import com.example.e321799.conecta4.model.RoundRepository
import com.example.e321799.conecta4.R
import com.example.e321799.conecta4.model.Round
import com.example.e321799.conecta4.model.RoundRepositoryFactory


/**
 * Clase que representa los mensajes de alerta de nuestra aplicacion
 */
class AlertDialogFragment : DialogFragment() {
    /**
     * Funcion que se ejecuta cuando se crea el dialogo
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = activity as AppCompatActivity?
        val alertDialogBuilder = AlertDialog.Builder(getActivity())
        alertDialogBuilder.setTitle(R.string.game_over)
        alertDialogBuilder.setMessage(R.string.game_over_message)
        alertDialogBuilder.setPositiveButton(R.string.yes_confirmation) { dialog, which ->
          /* RoundRepository.addRound()
            if (activity is RoundListActivity)
                activity.onRoundUpdated()
            else
                activity?.finish()*/
            val round = Round(/* SettingsActivity.getBoardSize(this).toInt() */)
            round.firstPlayerName = "OPEN_ROUND"
            round.firstPlayerUUID = "Random"
            round.secondPlayerName = SettingsActivity.getPlayerName(context!!)
            round.secondPlayerUUID = SettingsActivity.getPlayerUUID(context!!)
            val repository = RoundRepositoryFactory.createRepository(context!!)
            val callback = object : RoundRepository.BooleanCallback {
                override fun onResponse(response: Boolean) {
                    if (response == false)
                        /*Snackbar.make(this@AlertDialogFragment.view!!.findViewById(R.id.title),
                            R.string.error_adding_round, Snackbar.LENGTH_LONG).show()*/
                    else {
                        /*Snackbar.make(this@AlertDialogFragment.view!!.findViewById(R.id.title),
                            "New " + round.title + " added", Snackbar.LENGTH_LONG).show()*/
                    }
                }
            }
            repository?.addRound(round, callback)
            if (activity is RoundListActivity) {
                startActivity(Intent(context!!, RoundListActivity::class.java))
            }
            else {
                startActivity(RoundActivity.newIntent(context!!, round.toJSONString()))
            }
            activity?.finish()
            dialog.dismiss()
        }
        alertDialogBuilder.setNegativeButton(R.string.no_confirmation,
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    if (activity is RoundActivity)
                        activity?.finish()
                    dialog.dismiss()
                }
            })
        return alertDialogBuilder.create()
    }
}
