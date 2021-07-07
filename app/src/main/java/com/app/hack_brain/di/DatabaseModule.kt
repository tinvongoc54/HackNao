package com.app.hack_brain.di

import android.content.Context
import com.app.hack_brain.data.local.db.AppDatabase
import com.app.hack_brain.repository.DatabaseRepository
import com.app.hack_brain.repository.DefaultDatabaseRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase(androidContext()) }
}

fun provideDatabase(context: Context): DatabaseRepository {
    return DefaultDatabaseRepository(AppDatabase.getInstance(context))
}