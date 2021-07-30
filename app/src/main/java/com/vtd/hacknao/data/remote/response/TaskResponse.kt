package com.vtd.hacknao.data.remote.response

import com.google.gson.annotations.SerializedName

data class TaskResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("task_name")
    val name: String
)
