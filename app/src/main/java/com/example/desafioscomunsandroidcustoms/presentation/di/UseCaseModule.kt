package com.example.desafioscomunsandroidcustoms.presentation.di

import com.example.desafioscomunsandroidcustoms.data.LocalSecureStorage
import com.example.desafioscomunsandroidcustoms.data.StorageType
import org.koin.dsl.module

val useCaseModule = module {
    single<StorageType> { LocalSecureStorage(get()) }
}