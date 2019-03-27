package com.example.e321799.conecta4.Model
import java.util.*
import TableroConecta4
import java.io.Serializable

class Round(val player_one_token: Int, val player_two_token: Int, val player_one_name: String,
            val player_two_name: String, val turno: Int, val board_string: String, val fichas: String) : Serializable {
    var id: String
    var title: String
    var date: String
    init {
        this.id = UUID.randomUUID().toString()
        title = "ROUND ${id.toString().substring(19,23).toUpperCase()}"
        date = Date().toString()
    }
}