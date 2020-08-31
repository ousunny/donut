package me.ousunny.donut.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.google.gson.Gson
import me.ousunny.donut.Action
import me.ousunny.donut.Macro
import java.lang.reflect.Type
import java.util.*
import com.google.gson.reflect.TypeToken

class MacroDbTable(context: Context) {
    private val TAG = MacroDbTable::class.simpleName

    private val dbHelper = DonutDb(context)

    val gson: Gson = Gson()

    fun store(macro: Macro): Long {
        val db = dbHelper.writableDatabase

        val values = ContentValues()
        with(values) {
            put(MacroEntry.TITLE_COL, macro.title)
            put(MacroEntry.ACTIONS_COL, actionsToString(macro.actions))
        }

        val id = db.transaction {
            insert(MacroEntry.TABLE_NAME, null, values)
        }

        Log.d(TAG, "Stored new macro to database ${macro}")

        return id
    }

    private fun stringToActions(str: String): List<Action> {
        if (str == null) return Collections.emptyList()

        val listType: Type = object: TypeToken<List<Action>>() {}.type

        return gson.fromJson(str, listType)
    }

    private fun actionsToString(actions: List<Action>): String {
        return gson.toJson(actions)
    }
}

private inline fun <T> SQLiteDatabase.transaction(function: SQLiteDatabase.() -> T): T {
    beginTransaction()
    var result = try {
        val returnValue = function()
        setTransactionSuccessful()

        returnValue
    } finally {
        endTransaction()
    }

    close()

    return result
}