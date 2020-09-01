package me.ousunny.donut.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
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

    fun getAllMacros(): MutableList<Macro> {
        val columns = arrayOf(MacroEntry._ID, MacroEntry.TITLE_COL, MacroEntry.ACTIONS_COL)
        val order = "${MacroEntry._ID} ASC"
        val db = dbHelper.readableDatabase
        val cursor = db.doQuery(MacroEntry.TABLE_NAME, columns, orderBy = order)

        val macros = mutableListOf<Macro>()
        while(cursor.moveToNext()) {
            val title = cursor.getString(MacroEntry.TITLE_COL)

            val actions = stringToActions(cursor.getString(MacroEntry.ACTIONS_COL))

            macros.add(Macro(title, actions))
        }
        cursor.close()

        return macros
    }

    private fun stringToActions(str: String): MutableList<Action> {
        if (str == null) return Collections.emptyList()

        val listType: Type = object: TypeToken<MutableList<Action>>() {}.type

        return gson.fromJson(str, listType)
    }

    private fun actionsToString(actions: MutableList<Action>): String {
        return gson.toJson(actions)
    }

    private fun Cursor.getString(columnName: String): String {
        return getString(getColumnIndex(columnName))
    }
}

private fun SQLiteDatabase.doQuery(tableName: String, columns: Array<String>, selection: String? = null,
                                   selectionArgs: Array<String>? = null, groupBy: String? = null,
                                   having: String? = null, orderBy: String? = null): Cursor {
    return query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy)
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