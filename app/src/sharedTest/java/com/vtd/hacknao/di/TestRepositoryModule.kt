package com.vtd.hacknao.di

import com.vtd.hacknao.repository.task.DefaultTaskRepository
import com.vtd.hacknao.repository.task.TaskRepository
import org.koin.dsl.module

val testRepositoryModule = module {
    single { providerFakeTaskRepository() }
}

fun providerFakeTaskRepository(): TaskRepository {
    return DefaultTaskRepository()
}
