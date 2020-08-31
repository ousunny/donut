package me.ousunny.donut

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Macro(val title: String, val actions: List<Action>) : Parcelable

fun getSampleMacros(): List<Macro> {
    return listOf(
        Macro("First macro", actions = listOf(
                Action("Action 1", position = "1.0,5.0"),
                Action("Action 2", position = "1.0,5.0")
            )),
        Macro("Second macro", actions = listOf(
            Action("Action 3", position = "1.0,5.0"),
            Action("Action 4", position = "1.0,5.0")
        )),
        Macro("Third macro", actions = listOf(
            Action("Action 5", position = "1.0,5.0"),
            Action("Action 6", position = "1.0,5.0")
        ))
    )
}