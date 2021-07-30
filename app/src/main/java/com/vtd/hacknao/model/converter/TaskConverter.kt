package com.vtd.hacknao.model.converter

import com.vtd.hacknao.data.local.entity.TaskEntity
import com.vtd.hacknao.data.remote.request.TaskRequest
import com.vtd.hacknao.data.remote.response.TaskResponse
import com.vtd.hacknao.model.uimodel.Task

object TaskConverter {

    fun convertTaskToTaskRequest(task: Task) = TaskRequest(
        taskName = task.name
    )

    fun convertTaskResponseToTask(taskResponse: TaskResponse) = Task(
        name = taskResponse.name
    )

    fun convertTaskEntityToTask(taskEntity: TaskEntity): Task = taskEntity
}
