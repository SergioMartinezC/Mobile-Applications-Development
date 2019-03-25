package com.example.e321799.conecta4.Model

object RoundRepository {
    val rounds = ArrayList<Round>()

<<<<<<< HEAD
=======
    init {
        for (i in 0..100) {
            rounds.add(Round())
        }
    }

>>>>>>> 1a08e1a8282dd472e11e4cfc5e58d80308ed1770
    fun getRound(id:String): Round {
        val round = rounds.find { it.id == id  }
        return round ?: throw Exception("Round not found.")
    }

    fun addRound(round: Round) {
        rounds.add(round)
    }
}