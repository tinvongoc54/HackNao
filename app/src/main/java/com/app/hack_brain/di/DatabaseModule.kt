package com.app.hack_brain.di

import android.content.Context
import com.app.hack_brain.data.local.db.AppDatabase
import com.app.hack_brain.data.local.db.DatabaseRepository
import com.app.hack_brain.data.local.db.DefaultDatabaseRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import timber.log.Timber

val databaseModule = module {
    single { provideDatabase(androidContext()) }
}

fun provideDatabase(context: Context): DatabaseRepository {
    Timber.i("create database")
    return DefaultDatabaseRepository(AppDatabase.getInstance(context))
}