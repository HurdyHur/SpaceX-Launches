package com.harry.spacexlaunches

import android.app.Application
import com.harry.spacexlaunches.di.launchesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class SpaceXApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SpaceXApplication)
            modules(launchesModule)
        }
    }
}
