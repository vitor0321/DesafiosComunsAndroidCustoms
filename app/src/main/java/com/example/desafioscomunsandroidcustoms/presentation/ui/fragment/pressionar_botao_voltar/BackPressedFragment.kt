package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.pressionar_botao_voltar

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.databinding.FragmentBackPressedBinding
import com.example.desafioscomunsandroidcustoms.util.toast
import com.example.desafioscomunsandroidcustoms.util.viewBinding
import timber.log.Timber

class BackPressedFragment : Fragment(R.layout.fragment_back_pressed) {

    private val binding by viewBinding(FragmentBackPressedBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            clearData()
            logForCrashlytics()
            findNavController().navigateUp()
        }
    }

    private fun clearData(){
        binding.cpfEntry.text?.clear()
    }

    private fun logForCrashlytics(){
        toast("Pressinei Back")
        Timber.d("LOGAR COMPORTAMENTO/ACAO DO USUARIO SE NECESSARIO (CRASHLYTICS)")
    }
}