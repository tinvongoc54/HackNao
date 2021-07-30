package com.vtd.hacknao.app

import android.app.Application
import androidx.work.Configuration
import com.vtd.hacknao.BuildConfig
import com.vtd.hacknao.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/5/20.
 */
class App : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        configTimber()
        configKoin()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return if (BuildConfig.DEBUG) {
            Configuration.Builder()
                .setMinimumLoggingLevel(android.util.Log.DEBUG)
                .build()
        } else {
            Configuration.Builder()
                .setMinimumLoggingLevel(android.util.Log.ERROR)
                .build()
        }
    }

    private fun configKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    appModule,
                    remoteModule,
                    localModule,
                    repositoryModule,
                    viewModelModule,
                    databaseModule
                )
            )
        }
    }

    private fun configTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    companion object {
        private var activityVisible = false
        fun setRunning(isRunning: Boolean) {
            activityVisible = isRunning
        }
        fun isRunning() = activityVisible
    }
}
