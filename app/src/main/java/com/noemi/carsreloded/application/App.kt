package com.noemi.carsreloded.application

import android.app.Application
import com.noemi.carsreloded.di.*
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
        initLogger()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)

            modules(dataManagerModule)
            modules(apiHelperModule)
            modules(dispatcherModule)
            modules(carNetworkModule)
            modules(carDaoModule)
            modules(carRemoteDataSource)
            modules(carRepositoryModule)
            modules(useCasesModule)
            modules(viewModelsModule)
        }
    }

    private fun initLogger() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
            .tag("CONTROLLER_LOGGER")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
            .build()

        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }
}