package com.app.hack_brain.model.converter

import com.app.hack_brain.data.local.entity.TaskEntity
import com.app.hack_brain.data.remote.request.TaskRequest
import com.app.hack_brain.data.remote.response.TaskResponse
import com.app.hack_brain.model.uimodel.Task

object TaskConverter {

    fun convertTaskToTaskRequest(task: Task) = TaskRequest(
        taskName = task.name
    )

    fun convertTaskResponseToTask(taskResponse: TaskResponse) = Task(
        name = taskResponse.name
    )

    fun convertTaskEntityToTask(taskEntity: TaskEntity): Task = taskEntity
}
