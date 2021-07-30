package com.vtd.hacknao.model.uimodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShortStory(
    val id: Int,
    val title: String,
    val content: String,
    val audio: String
): Parcelable