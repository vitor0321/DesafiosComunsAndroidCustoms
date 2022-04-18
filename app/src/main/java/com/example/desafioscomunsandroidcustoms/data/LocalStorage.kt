package com.example.desafioscomunsandroidcustoms.util

import android.content.SharedPreferences
import androidx.core.content.edit

interface StorageType {
    fun putString(key: String, value: String)
    fun getString(key: String): String?
    fun deleteString(key: String)
    fun contains(key: String): Boolean
}

//EXEMPLO DE UM ARMAZENAMENTO LOCAL BASEADO EM CHAVE-VALOR SIMPLES
class LocalStorage(private val preferences: SharedPreferences) : StorageType {
    override fun putString(key: String, value: String) = preferences.edit { putString(key, value) }

    override fun getString(key: String): String = preferences.getString(key, "") ?: ""

    override fun deleteString(key: String) = preferences.edit { remove(key) }

    override fun contains(key: String): Boolean = preferences.contains(key)
}

open class LocalSecureStorage( private val preferences: SharedPreferences): StorageType{
    override fun putString(key: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun getString(key: String): String? {
        TODO("Not yet implemented")
    }

    override fun deleteString(key: String) {
        TODO("Not yet implemented")
    }

    override fun contains(key: String): Boolean {
        TODO("Not yet implemented")
    }
}