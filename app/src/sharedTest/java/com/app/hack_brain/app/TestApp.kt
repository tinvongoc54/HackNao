package com.app.hack_brain.app

import android.app.Application
import com.app.hack_brain.di.appModule
import com.app.hack_brain.di.testLocalModule
import com.app.hack_brain.di.testRemoteModule
import com.app.hack_brain.di.testRepositoryModule
import com.app.hack_brain.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class TestApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TestApp)
            modules(
                listOf(
                    appModule,
                    testRemoteModule,
                    testLocalModule,
                    testRepositoryModule,
                    viewModelModule
                )
            )
        }
    }

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }
}
