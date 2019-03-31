package com.example.e321799.conecta4.Model

object RoundRepository {
    val rounds = ArrayList<Round>()

    init {

    }

    fun getRound(id: String): Round {
        val round = rounds.find { it.id == id }
        return round ?: throw Exception("Round not found.")
    }

    fun addRound() : String {
        var round = Round()
        rounds.add(round)
        return round.id
    }
}