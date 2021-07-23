package com.app.hack_brain.model.uimodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class Word(
    val id: Int = 0,
    val word: String = "",
    val phonetic: String = "",
    val meanings: String = "",
    var isChecked: Boolean = false
): Serializable