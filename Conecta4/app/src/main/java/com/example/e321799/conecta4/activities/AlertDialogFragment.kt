package com.example.e321799.conecta4.activities

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.example.e321799.conecta4.model.RoundRepository
import com.example.e321799.conecta4.R
import com.example.e321799.conecta4.model.Round
import com.example.e321799.conecta4.model.RoundRepositoryFactory


/**
 * Clase que representa los mensajes de alerta de nuestra aplicacion
 */
class AlertDialogFragment : DialogFragment() {



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = activity as AppCompatActivity?
        val alertDialogBuilder = AlertDialog.Builder(getActivity())

        alertDialogBuilder.setTitle(R.string.end_game)
        if (tag == "TABLAS") {
            alertDialogBuilder.setMessage(R.string.draw_message)
        } else if (tag == "WINNER") {
            alertDialogBuilder.setMessage(R.string.winner_message)
        } else {
            alertDialogBuilder.setMessage(R.string.game_over_message)
        }
        alertDialogBuilder.setPositiveButton(R.string.yes_confirmation) { dialog, _ ->
          /* RoundRepository.addRound()
            if (activity is RoundListActivity)
                activity.onRoundUpdated()
            else
                activity?.finish()*/
            val round = Round(/* SettingsActivity.getBoardSize(this).toInt() */)
            round.firstPlayerName = SettingsActivity.getPlayerName(context!!)
            round.firstPlayerUUID = SettingsActivity.getPlayerUUID(context!!)
            round.secondPlayerName = "OPEN_ROUND"
            round.secondPlayerUUID = "Random"
            val repository = RoundRepositoryFactory.createRepository(context!!)
            val callback = object : RoundRepository.BooleanCallback {
                override fun onResponse(ok: Boolean) {
                    if (ok == false)
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
        alertDialogBuilder.setNegativeButton(R.string.no_confirmation
        ) { dialog, _ ->
            if (activity is RoundActivity)
                activity.finish()
            else {
                var fragment = fragmentManager?.findFragmentById(R.id.detail_fragment_container)
                fragmentManager?.beginTransaction()?.remove(fragment!!)!!.commit()
            }
            dialog.dismiss()
        }
        return alertDialogBuilder.create()
    }
}
