package com.app.hack_brain.model.uimodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Unit(
    val unit: String = "",
    val engViePercent: Float = 0f,
    val vieEngPercent: Float = 0f,
    val soundPercent: Float = 0f,
    val words: List<Word> = listOf()
): Parcelable {
    fun checkDisable() = engViePercent > 50 && vieEngPercent > 50 && soundPercent > 50
}