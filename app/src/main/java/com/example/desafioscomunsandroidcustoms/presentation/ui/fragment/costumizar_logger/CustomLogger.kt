package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.costumizar_logger

import android.util.Log
import timber.log.Timber

// 1 - inserir dependencia no build.gradle
// 2 - criar a classe de log customizada para su empresa usar em produção
// 3 - criar sua classe de application para inicializar sues timbers
// 4 - adicionar sua classe de application no manifest

class CustomLogger : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR && t != null) {
            Timber.e(t)
            //Microsoft App center Crache
//        Crashes.trackError(t)
        }
    }
}