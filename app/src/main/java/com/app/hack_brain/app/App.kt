package com.app.hack_brain.app

import android.app.Application
import com.app.hack_brain.BuildConfig
import com.app.hack_brain.di.appModule
import com.app.hack_brain.di.localModule
import com.app.hack_brain.di.remoteModule
import com.app.hack_brain.di.repositoryModule
import com.app.hack_brain.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/5/20.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        configTimber()
        configKoin()
    }

    private fun configKoin() {
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, remoteModule, localModule, repositoryModule, viewModelModule))
        }
    }

    private fun configTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
