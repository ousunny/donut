package me.ousunny.donut

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Macro(val title: String, val actions: Array<Action>) : Parcelable

fun getSampleMacros(): Array<Macro> {
    return arrayOf(
        Macro("First macro", actions = arrayOf(
                Action("Action 1", position = "1.0,5.0"),
                Action("Action 2", position = "1.0,5.0")
            )),
        Macro("Second macro", actions = arrayOf(
            Action("Action 3", position = "1.0,5.0"),
            Action("Action 4", position = "1.0,5.0")
        )),
        Macro("Third macro", actions = arrayOf(
            Action("Action 5", position = "1.0,5.0"),
            Action("Action 6", position = "1.0,5.0")
        ))
    )
}