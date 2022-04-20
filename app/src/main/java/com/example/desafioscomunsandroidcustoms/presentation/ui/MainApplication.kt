package com.example.desafioscomunsandroidcustoms.presentation.ui

import android.app.Application
import androidx.viewbinding.BuildConfig
import com.example.desafioscomunsandroidcustoms.presentation.di.sharedPreferencesModule
import com.example.desafioscomunsandroidcustoms.presentation.di.useCaseModule
import com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.costumizar_logger.CustomLogger
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupLogging()
        modulesKoin()
    }

    private fun setupLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // Logs em produção usando o crashlytics ou outro
            Timber.plant(CustomLogger())
        }
    }

    private fun modulesKoin() {
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MainApplication)

            modules(
                sharedPreferencesModule,
                useCaseModule
            )
        }
    }
}