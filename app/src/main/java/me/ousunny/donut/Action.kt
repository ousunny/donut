package me.ousunny.donut

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Action(val title: String, val startDelay: Double = 0.0, val position: String): Parcelable