package me.ousunny.donut.db

import android.provider.BaseColumns

val DATABASE_NAME = "donut.db"
val DATABASE_VERSION = 10

object MacroEntry : BaseColumns {
    val TABLE_NAME = "macro"
    val _ID = "id"
    val TITLE_COL = "title"
    val ACTIONS_COL = "actions"
}