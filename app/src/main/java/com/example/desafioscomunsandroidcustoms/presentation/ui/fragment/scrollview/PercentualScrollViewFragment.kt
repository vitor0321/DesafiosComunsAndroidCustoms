package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.scrollview

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.databinding.FragmentPercentualScrollViewBinding
import com.example.desafioscomunsandroidcustoms.util.viewBinding

// 1) COMO CRIAR LAYOUT FLEXÍVEL PARA VÁRIOS TIPOS DE CELULARES
// 2) COMO ESPECIFICAR NO XML PORCENTAGENS DENTRO DE UM SCROLLVIEW (PORQUE FAZER ISSO)
// 3) USAR NA PRÁTICA (layout_constraintDimensionRatio)

// https://developer.android.com/reference/androidx/constraintlayout/widget/ConstraintLayout
class PercentualScrollViewFragment : Fragment(R.layout.fragment_percentual_scroll_view) {

    // VEJA VIDEO BINDING AUTOMATICO: https://youtu.be/qivrch6qxQw
    private val binding by viewBinding(FragmentPercentualScrollViewBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // FACA O QUE VC QUISER AQUI....
    }
}