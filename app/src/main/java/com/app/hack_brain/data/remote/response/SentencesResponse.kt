package com.app.hack_brain.data.remote.response

import com.google.gson.annotations.SerializedName

data class SentencesResponse(
    @SerializedName("_id")
    val id: String,
    @SerializedName("fields")
    val fields: DetailTranslateResponse
)