package com.example.e321799.conecta4.database

import android.database.Cursor
import android.database.CursorWrapper
import android.util.Log
import com.example.e321799.conecta4.model.Round
import es.uam.eps.multij.ExcepcionJuego
import com.example.e321799.conecta4.database.RoundDataBaseSchema.RoundTable
import com.example.e321799.conecta4.database.RoundDataBaseSchema.UserTable
class RoundCursorWrapper(cursor: Cursor) : CursorWrapper(cursor) {
    private val DEBUG = "DEBUG"
    val round: Round
        get() {
            val playername = getString(getColumnIndex(UserTable.Cols.PLAYERNAME))
            val playeruuid = getString(getColumnIndex(UserTable.Cols.PLAYERUUID))
            val rounduuid = getString(getColumnIndex(RoundTable.Cols.ROUNDUUID))
            val date = getString(getColumnIndex(RoundTable.Cols.DATE))
            val title = getString(getColumnIndex(RoundTable.Cols.TITLE))
            val size = getString(getColumnIndex(RoundTable.Cols.SIZE))
            val board = getString(getColumnIndex(RoundTable.Cols.BOARD))
            val round = Round()
            round.firstPlayerName = playername
            round.firstPlayerUUID =  playeruuid
            round.secondPlayerName = "OPEN_ROUND"
            round.secondPlayerUUID = "Random"
            round.id = rounduuid
            round.date = date
            round.title = title
            try {
                round.board.stringToTablero(board)
            } catch (e: ExcepcionJuego) {
                Log.d(DEBUG, "Error turning string into tablero")
            }
            return round
        }
}