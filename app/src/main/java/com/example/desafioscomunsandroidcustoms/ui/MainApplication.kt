package com.example.desafioscomunsandroidcustoms.ui

import android.app.Application
import com.example.desafioscomunsandroidcustoms.BuildConfig
import com.example.desafioscomunsandroidcustoms.ui.fragment.costumizar_logger.CustomLogger
import timber.log.Timber

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupLogging()
    }

    private fun setupLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // Logs em produção usando o crashlytics ou outro
            Timber.plant(CustomLogger())
        }
    }
}