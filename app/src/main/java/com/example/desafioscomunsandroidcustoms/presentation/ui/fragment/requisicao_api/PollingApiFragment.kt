package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.requisicao_api

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.databinding.FragmentCheckIfEmulatorBinding
import com.example.desafioscomunsandroidcustoms.databinding.FragmentPollingApiBinding
import com.example.desafioscomunsandroidcustoms.util.hasInternet
import com.example.desafioscomunsandroidcustoms.util.polling
import com.example.desafioscomunsandroidcustoms.util.toast
import com.example.desafioscomunsandroidcustoms.util.viewBinding

class PollingApiFragment : Fragment(R.layout.fragment_polling_api) {

    private val binding by viewBinding(FragmentPollingApiBinding::bind)

    private var stateSuccess = false
    private var stateError = false
    private var stateHasInternet = false
    private var stateCanceled = false

    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.simulateSuccessBtn.setOnClickListener {
            stateSuccess = !stateSuccess
        }

        binding.simulateErroBtn.setOnClickListener {
            stateError = !stateError
        }

        binding.simulateNoInternetBtn.setOnClickListener {
            stateHasInternet = !stateHasInternet
        }

        binding.simulateCanceledBtn.setOnClickListener {
            stateCanceled = !stateCanceled
        }

        binding.startPollingBtn.setOnClickListener {
            polling(
                isOffline = { !hasInternet() },
                onOffline = { toast("Sem Internet") },
                isCompleted = { stateSuccess },
                onCompleted = { toast("sucesso") },
                isError = { stateError },
                onError = { toast("erro") },
                isCanceled = { stateCanceled },
                onCanceled = { toast("Polling cancelado") }
            )
        }
    }

}