package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.requisicao_api

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

open class CoroutineContextProvider(
    open val ui: CoroutineContext = Dispatchers.Main,
    open val io: CoroutineContext = Dispatchers.IO
)