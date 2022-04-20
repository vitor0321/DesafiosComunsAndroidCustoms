package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.dialog_custumizados

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.databinding.FragmentCustomDialogBinding
import com.example.desafioscomunsandroidcustoms.databinding.FragmentFullscreenDialogBinding
import com.example.desafioscomunsandroidcustoms.util.*

class CustomDialogFragment : Fragment(R.layout.fragment_custom_dialog) {

    private val binding by viewBinding(FragmentCustomDialogBinding::bind)

    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            // PADRÃO
            defaultDialogBtn.setOnClickListener {
                showDefaultMaterialAlertDialog(
                    title = "Padrão",
                    message = "Mensgem Padrão",
                    positiveButtonLabel = "Aceitar",
                    positiveButtonClickListener = { toast("Tudo Certo!") },
                    negativeButtonLabel = "Fechar",
                    negativeButtonClickListener = { toast("Fechei!") })
            }

            // SEMI-CUSTOMIZADA
            customizedDialogBtn.setOnClickListener {
                showDefaultMaterialAlertDialogWithCustomStaticContent(
                    positiveButtonLabel = "OK",
                    positiveButtonClickListener = { toast("OK") },
                    customLayoutId = R.layout.dialog_alert_custom_success,
                    customBackgroundId = R.drawable.dialog_alert_custom_rounded_border
                )
            }

            // TOTALMENTE CUSTOMIZADA
            val customView =
                layoutInflater.inflate(R.layout.dialog_alert_custom_success_button, null)
            val dialog = createFullCustomAlertDialog(
                customView = customView,
                customBackgroundId = R.drawable.dialog_alert_custom_rounded_border
            )
            with(customView) {
                findViewById<ImageView>(R.id.custom_dialog_icon).setImageResource(R.drawable.ic_error)
                findViewById<TextView>(R.id.custom_dialog_title).text = "Já Te Inscrevesse?"
                findViewById<TextView>(R.id.custom_dialog_message).text =
                    "Deixa teu Like e Subscribe"
                findViewById<Button>(R.id.cancel_btn).setOnClickListener {
                    dialog.dismiss()
                    toast("Fechar")
                }
                findViewById<Button>(R.id.accept_btn).setOnClickListener {
                    dialog.dismiss()
                    toast("Entendi")
                }
            }
            fullCustomizedDialogBtn.setOnClickListener {
                dialog.show()
            }
        }
    }
}