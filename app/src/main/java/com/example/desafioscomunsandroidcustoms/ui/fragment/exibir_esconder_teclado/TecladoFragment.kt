package com.example.desafioscomunsandroidcustoms.ui.fragment.exibir_esconder_teclado

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.databinding.FragmentTecladoBinding
import com.example.desafioscomunsandroidcustoms.util.hideKeyboard
import com.example.desafioscomunsandroidcustoms.util.showKeyboard
import com.example.desafioscomunsandroidcustoms.util.viewBinding

class TecladoFragment : Fragment(R.layout.fragment_teclado) {

    private val binding by viewBinding(FragmentTecladoBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            mostrarTeclado.setOnClickListener { this@TecladoFragment.showKeyboard() }

            esconderTeclado.setOnClickListener { this@TecladoFragment.hideKeyboard() }
        }
    }
}
