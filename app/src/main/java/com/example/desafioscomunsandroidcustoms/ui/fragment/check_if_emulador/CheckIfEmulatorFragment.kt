package com.example.desafioscomunsandroidcustoms.ui.fragment.check_if_emulador

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.databinding.FragmentCheckIfEmulatorBinding
import com.example.desafioscomunsandroidcustoms.util.EmulatorDetector
import com.example.desafioscomunsandroidcustoms.util.viewBinding

class CheckIfEmulatorFragment : Fragment(R.layout.fragment_check_if_emulator) {

    private val binding by viewBinding(FragmentCheckIfEmulatorBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            checkButtonEmulador.setOnClickListener {
                when {
                    EmulatorDetector.isEmulator() -> textViewEmulador.text = "É um Emulador"
                    else -> textViewEmulador.text = "É um device"
                }
            }
        }
    }
}