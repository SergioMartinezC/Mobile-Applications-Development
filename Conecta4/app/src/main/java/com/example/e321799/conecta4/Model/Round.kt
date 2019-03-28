package com.example.e321799.conecta4.Model

import TableroConecta4
import java.util.*

class Round() {
    var id: String
    var title: String
    var date: String
    var board: TableroConecta4
    init {
        id = UUID.randomUUID().toString()
        title = "ROUND ${id.substring(19, 23).toUpperCase()}"
        date = Date().toString()
        board = TableroConecta4()
    }
}