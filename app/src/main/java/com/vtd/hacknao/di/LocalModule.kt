package com.vtd.hacknao.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.vtd.hacknao.data.local.sharedpfers.DefaultSharePrefsService
import com.vtd.hacknao.data.local.sharedpfers.SharePrefsService
import com.vtd.hacknao.data.local.sharedpfers.SharedPrefKeys
import com.vtd.hacknao.data.local.sharedpfers.SharedPrefsWrapper
import com.google.gson.Gson
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localModule = module {
    single { providerSharedPrefs(androidApplication()) }
    single { providerSharedPrefsWrapper(get(), get()) }
    single { provideSharePrefServices(get(), get()) }
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

fun provideSharePrefServices(sharedPrefsWrapper: SharedPrefsWrapper, gson: Gson): SharePrefsService {
    return DefaultSharePrefsService(sharedPrefsWrapper, gson)
}
