package com.vtd.hacknao.di

import android.content.Context
import com.vtd.hacknao.data.local.db.AppDatabase
import com.vtd.hacknao.repository.DatabaseRepository
import com.vtd.hacknao.repository.DefaultDatabaseRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase(androidContext()) }
}

fun provideDatabase(context: Context): DatabaseRepository {
    return DefaultDatabaseRepository(AppDatabase.getInstance(context))
}