package com.app.hack_brain.repository

import com.app.hack_brain.data.remote.response.TaskResponse
import com.app.hack_brain.repository.task.TaskRepository

class FakeTaskRepository(var shouldReturnError: Boolean = false) : TaskRepository {

    private val photosServiceData: LinkedHashMap<String, TaskResponse> = LinkedHashMap()
}
