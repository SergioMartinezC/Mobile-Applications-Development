package com.example.e321799.conecta4.model

/**
 * Repositorio donde van a estar todas las partidas guardadas
 */
object RoundRepository {
    val rounds = ArrayList<Round>()

    /**
     * De vuelve el round con id [id]
     * @return ronda si se encuentra en la lista
     */
    fun getRound(id: String): Round {
        val round = rounds.find { it.id == id }
        return round ?: throw Exception("Round not found.")
    }

    /**
     * Añade una ronda vacía a la lista
     * @return el id de la ronda que se crea
     */
    fun addRound() : String {
        var round = Round()
        rounds.add(round)
        return round.id
    }
}