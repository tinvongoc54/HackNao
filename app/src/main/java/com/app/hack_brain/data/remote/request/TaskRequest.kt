package com.app.hack_brain.data.remote.request

import com.google.gson.annotations.SerializedName

class TaskRequest(
    @SerializedName("task_name")
    val taskName: Any
)
