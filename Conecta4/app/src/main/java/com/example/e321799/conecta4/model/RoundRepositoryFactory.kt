package com.example.e321799.conecta4.model

import android.content.Context
import android.preference.PreferenceManager
import com.example.e321799.conecta4.activities.RoundAdapter
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

        return repository
    }
}