package com.app.hack_brain.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailTranslateResponse(
    @SerializedName("en")
    val en: String,
    @SerializedName("vi")
    val vi: String
)