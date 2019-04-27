package com.example.e321799.conecta4.activities

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.example.e321799.conecta4.model.RoundRepository
import com.example.e321799.conecta4.R


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
            RoundRepository.addRound()
            if (activity is RoundListActivity)
                activity.onRoundUpdated()
            else
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