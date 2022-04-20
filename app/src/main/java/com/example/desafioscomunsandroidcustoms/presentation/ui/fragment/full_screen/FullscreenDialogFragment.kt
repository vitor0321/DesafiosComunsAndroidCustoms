package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.full_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.databinding.FragmentFullscreenDialogBinding
import com.example.desafioscomunsandroidcustoms.util.showFullscreenAlertDialog
import com.example.desafioscomunsandroidcustoms.util.viewBinding

class FullscreenDialogFragment : Fragment(R.layout.fragment_fullscreen_dialog) {

    private val binding by viewBinding(FragmentFullscreenDialogBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dialogBtn.setOnClickListener {
            showFullscreenAlertDialog(
                title = "Humm algo está errado",
                message = "Vamos trabalhar para ",
                cancelButtonLabel = "Cancelar"
            )
        }

    }
}