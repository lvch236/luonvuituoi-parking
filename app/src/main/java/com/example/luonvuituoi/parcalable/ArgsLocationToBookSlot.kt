package com.example.luonvuituoi.parcalable

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArgsLocationToBookSlot(
    val nameMall: String,
    val address: Int
) :
    Parcelable {
}