package com.app.hack_brain.di

import com.app.hack_brain.repository.task.DefaultTaskRepository
import com.app.hack_brain.repository.task.TaskRepository
import org.koin.dsl.module

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/5/20.
 */

val repositoryModule = module {
    single { providerTaskRepository() }
}

fun providerTaskRepository(): TaskRepository {
    return DefaultTaskRepository()
}
