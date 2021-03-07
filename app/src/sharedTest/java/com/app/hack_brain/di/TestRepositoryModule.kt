package com.app.hack_brain.di

import com.app.hack_brain.repository.task.DefaultTaskRepository
import com.app.hack_brain.repository.task.TaskRepository
import org.koin.dsl.module

val testRepositoryModule = module {
    single { providerFakeTaskRepository() }
}

fun providerFakeTaskRepository(): TaskRepository {
    return DefaultTaskRepository()
}
