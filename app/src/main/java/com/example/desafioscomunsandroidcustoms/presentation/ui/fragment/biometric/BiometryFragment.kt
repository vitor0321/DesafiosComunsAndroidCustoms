package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.biometric

import android.os.Bundle
import android.view.View
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.Fragment
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.databinding.FragmentBiometryBinding
import com.example.desafioscomunsandroidcustoms.util.promptBiometricChecker
import com.example.desafioscomunsandroidcustoms.util.toast
import com.example.desafioscomunsandroidcustoms.util.viewBinding

class BiometryFragment : Fragment(R.layout.fragment_biometry) {

    private val binding by viewBinding(FragmentBiometryBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.biometryButton.setOnClickListener {
            promptBiometricChecker(
                "Desbloqueia Por Favor",
                "Use Seu FingerPrint",
                "Cancelar",
                confirmationRequired = true,
                null,
                { result ->
                    when (result.authenticationType) {
                        BiometricPrompt.AUTHENTICATION_RESULT_TYPE_BIOMETRIC -> {
                            toast("sucesso fingerprint or face!")
                        }
                        BiometricPrompt.AUTHENTICATION_RESULT_TYPE_UNKNOWN -> {
                            toast("sucesso por meio legado ou desconhecido")
                        }
                        BiometricPrompt.AUTHENTICATION_RESULT_TYPE_DEVICE_CREDENTIAL -> {
                            toast("sucesso pin, pattern or password")
                        }
                    }
                },
                { error, errorMsg ->
                    toast("$error: $errorMsg")
                })
        }
    }
}