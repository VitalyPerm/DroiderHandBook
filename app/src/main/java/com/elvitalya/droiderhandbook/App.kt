package com.elvitalya.droiderhandbook

import android.app.Application
import com.elvitalya.data.di.dataModules
import com.elvitalya.droiderhandbook.di.domainModules
import com.elvitalya.presentation.di.presentationModule
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
// еуые