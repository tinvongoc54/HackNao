package com.vtd.hacknao.repository

import com.vtd.hacknao.data.remote.response.TaskResponse
import com.vtd.hacknao.repository.task.TaskRepository

class FakeTaskRepository(var shouldReturnError: Boolean = false) : TaskRepository {

    private val photosServiceData: LinkedHashMap<String, TaskResponse> = LinkedHashMap()
}
