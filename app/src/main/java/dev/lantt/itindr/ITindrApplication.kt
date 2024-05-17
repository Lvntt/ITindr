package dev.lantt.itindr

import android.app.Application
import dev.lantt.itindr.di.coreModule
import dev.lantt.itindr.di.dataModule
import dev.lantt.itindr.di.domainModule
import dev.lantt.itindr.di.networkModule
import dev.lantt.itindr.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ITindrApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ITindrApplication)
            modules(
                coreModule(),
                networkModule(),
                dataModule(),
                domainModule(),
                presentationModule()
            )
        }
    }

}