package com.example.desafioscomunsandroidcustoms.presentation.ui.defaul_exception

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

//DefaultExceptionHandler
// é usado para capturar qualquer exeção não tratado pelo app
//dando uma oportunidade para tratar e limpar dados senciveis
// antes de fechar o app
class ClearableCoroutineScope(context: CoroutineContext): CoroutineScope {

    private var onViewDetachJob = Job()

    //agrupar ou combinar coroutines
    override val coroutineContext: CoroutineContext = context + onViewDetachJob

    fun clearScope(){
        onViewDetachJob.cancel()
    }
}