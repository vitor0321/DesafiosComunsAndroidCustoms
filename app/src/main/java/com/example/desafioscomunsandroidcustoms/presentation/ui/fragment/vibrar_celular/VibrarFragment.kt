package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.vibrar_celular

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.databinding.FragmentVibrarBinding
import com.example.desafioscomunsandroidcustoms.util.vibrate
import com.example.desafioscomunsandroidcustoms.util.viewBinding

class VibrarFragment : Fragment(R.layout.fragment_vibrar) {

    private val binding by viewBinding(FragmentVibrarBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            vibrateButton.setOnClickListener {
                this@VibrarFragment.vibrate(2000L)
            }
        }
    }
}