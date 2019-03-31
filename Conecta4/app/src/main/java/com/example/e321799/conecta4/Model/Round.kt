package com.example.e321799.conecta4.Model

import TableroConecta4
import java.util.*
/**
 * Representacion de una ronda
 *
 *
 *
 * @constructor Crea una ronda con un tablero vacio
 */
class Round() {
    var id: String /*Id de la ronda*/
    var title: String /*Título de la partida guaradada como ronda*/
    var date: String /*Fecha de guardado*/
    var board: TableroConecta4 /*Tablero */
    init {
        id = UUID.randomUUID().toString()
        title = "ROUND ${id.substring(19, 23).toUpperCase()}"
        date = Date().toString()
        board = TableroConecta4()
    }
}