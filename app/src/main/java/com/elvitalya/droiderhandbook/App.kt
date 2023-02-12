package com.elvitalya.droiderhandbook

import android.app.Application
import com.elvitalya.droiderhandbook.data.di.dataModules
import com.elvitalya.droiderhandbook.domain.di.domainModules
import com.elvitalya.droiderhandbook.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(domainModules + dataModules + presentationModule)
        }
    }
}