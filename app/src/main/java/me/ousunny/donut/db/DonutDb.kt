package me.ousunny.donut.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DonutDb(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val SQL_CREATE_QUERIES = "CREATE TABLE ${MacroEntry.TABLE_NAME} (" +
            "${MacroEntry._ID} INTEGER PRIMARY KEY," +
            "${MacroEntry.TITLE_COL} TEXT," +
            "${MacroEntry.ACTIONS_COL} TEXT" +
            ")"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${MacroEntry.TABLE_NAME}"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_QUERIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
}