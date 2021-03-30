package com.app.hack_brain.model.uimodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Word(
    val id: Int,
    val word: String,
    val phonetic: String,
    val meanings: String
): Parcelable