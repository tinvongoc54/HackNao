package com.app.hack_brain.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.app.hack_brain.data.local.db.AppDatabase
import com.app.hack_brain.data.local.db.DatabaseHelper
import com.app.hack_brain.data.local.db.DatabaseHelperImpl
import com.google.gson.Gson
import com.app.hack_brain.data.local.sharedpfers.SharedPrefKeys
import com.app.hack_brain.data.local.sharedpfers.SharedPrefsWrapper
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {
    single { providerSharedPrefs(androidApplication()) }
    single { providerSharedPrefsWrapper(get(), get()) }
    single { provideDatabase(androidContext()) }
}

fun providerSharedPrefs(app: Application): SharedPreferences {
    return app.getSharedPreferences(
        SharedPrefKeys.SHARED_PREFS_NAME, Context.MODE_PRIVATE
    )
}

fun providerSharedPrefsWrapper(
    sharedPreferences: SharedPreferences,
    gson: Gson
): SharedPrefsWrapper {
    return SharedPrefsWrapper(sharedPreferences, gson)
}

fun provideDatabase(context: Context): DatabaseHelper {
    return DatabaseHelperImpl(AppDatabase.getInstance(context))
}
