package com.app.hack_brain.data.remote.response

import com.google.gson.annotations.SerializedName

data class TranslateResponse(
    @SerializedName("language")
    val language: String,
    @SerializedName("sentences")
    val sentences: List<SentencesResponse>
)