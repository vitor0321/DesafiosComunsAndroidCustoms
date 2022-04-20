package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.full_screen

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.databinding.DialogAlertFullscreenBinding
import com.example.desafioscomunsandroidcustoms.util.viewBinding

class FullscreenAlertDialog(
    val title: String = "",
    val message: String = "",
    val positiveLabel: String = "",
    val positiveAction: () -> Unit = {},
    val cancelLabel: String? = null,
    val cancelAction: () -> Unit = {},
    val dismissAction: () -> Unit = {},
) : DialogFragment(R.layout.dialog_alert_fullscreen) {

    private val binding by viewBinding(DialogAlertFullscreenBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            header.text = title
            body.text = message
            nextButton.text = positiveLabel
            nextButton.setOnClickListener {
                dismiss()
                positiveAction()
            }
            cancelLabel?.let { cancelButton.visibility }
            cancelButton.text = cancelLabel
            cancelButton.setOnClickListener {
                dismiss()
                cancelAction()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissAction()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): android.app.Dialog {
        setStyle(STYLE_NO_FRAME, R.style.Theme_DesafiosComunsAndroidCustoms_Fullscreen)
        return super.onCreateDialog(savedInstanceState)
    }
}