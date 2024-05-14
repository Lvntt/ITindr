package dev.lantt.itindr

import android.app.Application
import dev.lantt.itindr.di.coreModule
import org.koin.core.context.startKoin

class ITindrApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                coreModule()
            )
        }
    }

}