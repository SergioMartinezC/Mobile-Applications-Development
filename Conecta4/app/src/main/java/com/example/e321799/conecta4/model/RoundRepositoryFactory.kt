package com.example.e321799.conecta4.model

import android.content.Context
import android.preference.PreferenceManager
import com.example.e321799.conecta4.database.DataBase
import com.example.e321799.conecta4.firebase.FBDataBase

object RoundRepositoryFactory {
    private val DATABASE_MODE = "database_mode"
    fun createRepository(context: Context): RoundRepository? {
        val sharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(context)
        val repository: RoundRepository
        repository = if (sharedPreferences.getBoolean(DATABASE_MODE, false)) FBDataBase() else DataBase(context)
        try {
            repository.open()
        } catch (e: Exception) {
            return null
        }
        if (repository is FBDataBase) {
            var callback = object: RoundRepository.RoundsCallback{
                override fun onResponse(rounds: List<Round>) {
                    for (round in rounds) {
                        if (round.id == " ") {

                        }
                    }
                }
                override fun onError(error: String) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            }
            repository.startListeningChanges(callback)
        }
        return repository
    }
}