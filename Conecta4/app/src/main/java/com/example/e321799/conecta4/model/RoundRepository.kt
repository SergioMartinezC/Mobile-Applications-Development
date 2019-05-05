package com.example.e321799.conecta4.model

/**
 * @brief Interfaz con las funciones para gestionar las rondas
 */
interface RoundRepository {
    @Throws(Exception::class)
    fun open()
    fun close()
    interface LoginRegisterCallback {
        fun onLogin(playerUuid: String)
        fun onError(error: String)
    }
    fun login(playername: String, password: String, callback: LoginRegisterCallback)
    fun register(playername: String, password: String, callback: LoginRegisterCallback)

    interface BooleanCallback {
        fun onResponse(ok: Boolean)
    }
    fun getRounds(playeruuid: String, orderByField: String, group: String,
                  callback: RoundsCallback)
    fun addRound(round: Round, callback: BooleanCallback)
    fun updateRound(round: Round, callback: BooleanCallback)
    fun joinRound(round: Round, callback: BooleanCallback)
    interface RoundsCallback {
        fun onResponse(rounds: List<Round>)
        fun onError(error: String)
    }
}