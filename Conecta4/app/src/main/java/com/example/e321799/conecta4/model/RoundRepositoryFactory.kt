package com.example.e321799.conecta4.model

import android.content.Context
import com.example.e321799.conecta4.database.DataBase
import com.example.e321799.conecta4.firebase.FBDataBase

object RoundRepositoryFactory {
    private val LOCAL = false
    fun createRepository(context: Context): RoundRepository? {
        val repository: RoundRepository
        repository = if (LOCAL) DataBase(context) else FBDataBase()
        try {
            repository.open()
        } catch (e: Exception) {
            return null
        }
        return repository
    }
}