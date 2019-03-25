package com.example.e321799.conecta4.Model

object RoundRepository {
    val rounds = ArrayList<Round>()

    init {
        for (i in 0..100) {
            rounds.add(Round())
        }
    }

    fun getRound(id:String): Round {
        val round = rounds.find { it.id == id  }
        return round ?: throw Exception("Round not found.")
    }

    fun addRound(round: Round) {
        rounds.add(round)
    }
}