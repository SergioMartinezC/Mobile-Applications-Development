package com.example.e321799.conecta4.model

import android.content.Context
import com.example.e321799.conecta4.database.DataBase
object RoundRepositoryFactory {
    private val LOCAL = true
    fun createRepository(context: Context): RoundRepository? {
        val repository: RoundRepository
        repository = if (LOCAL) DataBase(context) else DataBase(context)
        try {
            repository.open()
        } catch (e: Exception) {
            return null
        }
        return repository
    }
}