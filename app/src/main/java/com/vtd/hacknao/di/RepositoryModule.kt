package com.vtd.hacknao.di

import com.vtd.hacknao.repository.DefaultGeneralRepository
import com.vtd.hacknao.repository.GeneralRepository
import com.vtd.hacknao.repository.task.DefaultTaskRepository
import com.vtd.hacknao.repository.task.TaskRepository
import org.koin.dsl.module

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/5/20.
 */

val repositoryModule = module {
    single { providerTaskRepository() }
    single { providerGeneralRepository() }
}

fun providerTaskRepository(): TaskRepository {
    return DefaultTaskRepository()
}

fun providerGeneralRepository(): GeneralRepository {
    return DefaultGeneralRepository()
}
