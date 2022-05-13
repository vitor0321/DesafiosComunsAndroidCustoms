package com.example.desafioscomunsandroidcustoms.presentation.ui

import android.app.Application
import androidx.viewbinding.BuildConfig
import com.example.desafioscomunsandroidcustoms.presentation.di.sharedPreferencesModule
import com.example.desafioscomunsandroidcustoms.presentation.di.useCaseModule
import com.example.desafioscomunsandroidcustoms.presentation.ui.defaul_exception.ClearableCoroutineScope
import com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.costumizar_logger.CustomLogger
import com.example.desafioscomunsandroidcustoms.util.migration.MigrationSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupLogging()
        setupDefaultExceptionHandler()
        modulesKoin()

        //migrarar o SharedPreference de um legado para um novo APP
        MigrationSharedPreferences(this).migrateOnlyOne()
    }

    private fun setupLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // Logs em produção usando o crashlytics ou outro
            Timber.plant(CustomLogger())
        }
    }

    private fun setupDefaultExceptionHandler() {
        //pega o default Uncaught Exception handler para repassar os erros
        val existingHandler = Thread.getDefaultUncaughtExceptionHandler()
        //intercepta os erros, faz o que for preciso e so depois disso lança os erros
        Thread.setDefaultUncaughtExceptionHandler { thread, exception ->
            ClearableCoroutineScope(Dispatchers.Default).launch {
                Timber.v("clearing cookies")
                //fazer tudo que for pedido pela auditoria da empresa
            }
            existingHandler?.uncaughtException(thread, exception)
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