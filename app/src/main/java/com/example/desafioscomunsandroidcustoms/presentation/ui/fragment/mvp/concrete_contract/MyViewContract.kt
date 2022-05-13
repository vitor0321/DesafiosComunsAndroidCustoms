package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.mvp.concrete_contract

import com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.mvp.base_contract.PresenterContract
import com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.mvp.base_contract.ViewContract

// CADA VIEW PRECISA DE UM CONTRATO, ASSIM FICA CLARO O USO DEPOIS
interface MyViewContract {

    interface View : ViewContract {
        // AQUI VC TEM A OPORTUNIDADE DE DEFINIR OS METODOS DO SEU CASO DE USO
        fun showLottieAnim(animId: Int)
        fun hideKeyboardOnButtonClick()
    }

    interface Presenter : PresenterContract<View> {
        // AQUI VC DEFINE OS METODOS DO SEU CASO DE USO
        fun computeMaxValue(aValue: String, bValue: String): String
    }
}