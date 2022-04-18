package com.example.desafioscomunsandroidcustoms.util

import android.os.Build

// Singleton in Kotlin(uma class object por si só já é um singleton)
//Singleton é uma classe que tem uma unica instancia dela
object EmulatorDetector {

    private const val EMULATOR_BUILD_PROPERTY = "generic"

    fun isEmulator(): Boolean = Build.FINGERPRINT.contains(EMULATOR_BUILD_PROPERTY)
}