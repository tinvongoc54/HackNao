package com.vtd.hacknao.app

import android.app.Application
import com.vtd.hacknao.di.appModule
import com.vtd.hacknao.di.testLocalModule
import com.vtd.hacknao.di.testRemoteModule
import com.vtd.hacknao.di.testRepositoryModule
import com.vtd.hacknao.di.viewModelModule
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
