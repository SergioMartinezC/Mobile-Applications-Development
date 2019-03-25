package com.example.e321799.conecta4.Model
import java.util.*
import TableroConecta4
<<<<<<< HEAD
import java.io.Serializable

class Round(val ficha1: Int, val ficha2: Int, val name_player_one: String,
            val name_player_two: String, val turno: Int, val board: String, val fichas: String) : Serializable {
    var id: String
    var title: String
    var date: String
    /* val tablero = mutableListOf<MutableList<Int>>() */
    var player_one_name: String
    var player_two_name: String
    var player_one_token: Int
    var player_two_token: Int
    var turn: Int
    var tablero: String
    var fichasEnColumna: String


=======

class Round() {
    var id: String
    var title: String
    var date: String
    var board: TableroConecta4
    val tablero = mutableListOf<MutableList<Int>>()
>>>>>>> 1a08e1a8282dd472e11e4cfc5e58d80308ed1770

    init {
        id = UUID.randomUUID().toString()
        title = "ROUND ${id.toString().substring(19,23).toUpperCase()}"
        date = Date().toString()
<<<<<<< HEAD
        player_one_name = name_player_one
        player_two_name = name_player_two
        player_one_token = ficha1
        player_two_token = ficha2
        turn = turno
        tablero = board
        fichasEnColumna = fichas
    }
=======
        board = TableroConecta4(tablero)
    }


>>>>>>> 1a08e1a8282dd472e11e4cfc5e58d80308ed1770
}