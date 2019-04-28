package com.example.e321799.conecta4.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.e321799.conecta4.model.Round
import com.example.e321799.conecta4.model.RoundRepository
import java.sql.SQLException
import java.util.*


class DataBase(context: Context) : RoundRepository {
    private val DEBUG_TAG = "DEBUG"
    private val helper: DatabaseHelper
    private var db: SQLiteDatabase? = null
    init {
        helper = DatabaseHelper(context)
    }

    private class DatabaseHelper(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        override fun onCreate(db: SQLiteDatabase) {
            createTable(db)
        }
        override fun onUpgrade(db: SQLiteDatabase, oldVer: Int, newVer: Int) {
            db.execSQL("DROP TABLE IF EXISTS " + RoundDataBaseSchema.UserTable.NAME)
            db.execSQL("DROP TABLE IF EXISTS " + RoundDataBaseSchema.RoundTable.NAME)
            createTable(db)
        }

        private fun createTable(db: SQLiteDatabase) {
            val str1 = ("CREATE TABLE " + RoundDataBaseSchema.UserTable.NAME + " ("
                    + "_id integer primary key autoincrement, "
                    + RoundDataBaseSchema.UserTable.Cols.PLAYERUUID + " TEXT UNIQUE, "
                    + RoundDataBaseSchema.UserTable.Cols.PLAYERNAME + " TEXT UNIQUE, "
                    + RoundDataBaseSchema.UserTable.Cols.PLAYERPASSWORD + " TEXT);")
            val str2 = ("CREATE TABLE " + RoundDataBaseSchema.RoundTable.NAME + " ("
                    + "_id integer primary key autoincrement, "
                    + RoundDataBaseSchema.RoundTable.Cols.ROUNDUUID + " TEXT UNIQUE, "
                    + RoundDataBaseSchema.RoundTable.Cols.PLAYERUUID + " TEXT REFERENCES "
                    + RoundDataBaseSchema.UserTable.Cols.PLAYERUUID + ", "
                    + RoundDataBaseSchema.RoundTable.Cols.DATE + " TEXT, "
                    + RoundDataBaseSchema.RoundTable.Cols.TITLE + " TEXT, "
                    + RoundDataBaseSchema.RoundTable.Cols.SIZE + " TEXT, "
                    + RoundDataBaseSchema.RoundTable.Cols.BOARD + " TEXT);")
            try {
                db.execSQL(str1)
                db.execSQL(str2)
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
    }

    @Throws(SQLException::class)
    override fun open() {
        db = helper.writableDatabase
    }

    override fun close() {
        db?.close()
    }

    override fun login(playername: String, playerpassword: String,
                       callback: RoundRepository.LoginRegisterCallback) {
        Log.d(DEBUG_TAG, "Login $playername")
        val cursor = db!!.query(
            RoundDataBaseSchema.UserTable.NAME,
            arrayOf(RoundDataBaseSchema.UserTable.Cols.PLAYERUUID),
            RoundDataBaseSchema.UserTable.Cols.PLAYERNAME + " = ? AND "
                    + RoundDataBaseSchema.UserTable.Cols.PLAYERPASSWORD + " = ?",
            arrayOf(playername, playerpassword),
            null, null, null)
        val count = cursor.count
        val uuid =
            if (count == 1 && cursor.moveToFirst()) cursor.getString(0) else ""
        cursor.close()
        if (count == 1)
            callback.onLogin(uuid)
        else
            callback.onError("Username or password incorrect.")
    }

    override fun register(playername: String, password: String,
                          callback: RoundRepository.LoginRegisterCallback) {
        val values = ContentValues()
        val uuid = UUID.randomUUID().toString()
        values.put(RoundDataBaseSchema.UserTable.Cols.PLAYERUUID, uuid)
        values.put(RoundDataBaseSchema.UserTable.Cols.PLAYERNAME, playername)
        values.put(RoundDataBaseSchema.UserTable.Cols.PLAYERPASSWORD, password)
        val id = db!!.insert(RoundDataBaseSchema.UserTable.NAME, null, values)
        if (id < 0)
            callback.onError("Error inserting new player named $playername")
        else
            callback.onLogin(uuid)
    }

    override fun addRound(round: Round, callback: RoundRepository.BooleanCallback) {
        val values = getContentValues(round)
        val id = db!!.insert(RoundDataBaseSchema.RoundTable.NAME, null, values)
        callback.onResponse(id >= 0)

    }

    private fun getContentValues(round: Round): ContentValues {
        val values = ContentValues()
        values.put(RoundDataBaseSchema.RoundTable.Cols.PLAYERUUID, round.secondPlayerUUID)
        values.put(RoundDataBaseSchema.RoundTable.Cols.ROUNDUUID, round.id)
        values.put(RoundDataBaseSchema.RoundTable.Cols.DATE, round.date)
        values.put(RoundDataBaseSchema.RoundTable.Cols.TITLE, round.title)
        values.put(RoundDataBaseSchema.RoundTable.Cols.SIZE, 8)
        values.put(RoundDataBaseSchema.RoundTable.Cols.BOARD, round.board.tableroToString())
        return values
    }

    override fun updateRound(round: Round, callback: RoundRepository.BooleanCallback) {
        val values = getContentValues(round)
        val id = db!!.update(
            RoundDataBaseSchema.RoundTable.NAME, values,
            RoundDataBaseSchema.RoundTable.Cols.ROUNDUUID + " = ?",
            arrayOf(round.id))
        callback.onResponse(id >= 0)
    }

    override fun getRounds(playeruuid: String, orderByField: String, group: String,
                           callback: RoundRepository.RoundsCallback) {
        val rounds = ArrayList<Round>()
        val cursor = queryRounds()

        cursor?.moveToFirst()
        while (!cursor!!.isAfterLast()) {
            val round = cursor?.round
            if (round.secondPlayerUUID.equals(playeruuid))
                rounds.add(round)
            cursor?.moveToNext()
        }
        cursor?.close()
        if (cursor?.getCount() > 0)
            callback.onResponse(rounds)
        else
            callback.onError("No rounds found in database")
    }

    private fun queryRounds(): RoundCursorWrapper? {
        val sql = "SELECT " + RoundDataBaseSchema.UserTable.Cols.PLAYERNAME + ", " +
                RoundDataBaseSchema.UserTable.Cols.PLAYERUUID + ", " +
                RoundDataBaseSchema.RoundTable.Cols.ROUNDUUID + ", " +
                RoundDataBaseSchema.RoundTable.Cols.DATE + ", " +
                RoundDataBaseSchema.RoundTable.Cols.TITLE + ", " +
                RoundDataBaseSchema.RoundTable.Cols.SIZE + ", " +
                RoundDataBaseSchema.RoundTable.Cols.BOARD + " " +
                "FROM " + RoundDataBaseSchema.UserTable.NAME + " AS p, " +
                RoundDataBaseSchema.RoundTable.NAME + " AS r " +
                "WHERE " + "p." + RoundDataBaseSchema.UserTable.Cols.PLAYERUUID + "=" +
                "r." + RoundDataBaseSchema.RoundTable.Cols.PLAYERUUID + ";"
        val cursor = db!!.rawQuery(sql, null)
        return RoundCursorWrapper(cursor)
    }

    companion object {
        private val DATABASE_NAME = "er.db"
        private val DATABASE_VERSION = 1
    }


}