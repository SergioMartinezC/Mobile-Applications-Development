package com.example.e321799.conecta4.Model
import java.util.*
import TableroConecta4

class Round() {
    var id: String
    var title: String
    var date: String
    var board: TableroConecta4
    val tablero = mutableListOf<MutableList<Int>>()

    init {
        id = UUID.randomUUID().toString()
        title = "ROUND ${id.toString().substring(19,23).toUpperCase()}"
        date = Date().toString()
        board = TableroConecta4(tablero)
    }


}