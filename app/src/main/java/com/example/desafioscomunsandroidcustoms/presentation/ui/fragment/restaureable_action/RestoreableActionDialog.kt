package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.restaureable_action

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.databinding.FragmentRestorableActionDialogBinding
import com.google.android.material.button.MaterialButton
import com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.restaureable_action.RestoreableAction.Positive
import com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.restaureable_action.RestoreableAction.Negative
import com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.restaureable_action.RestoreableAction.Dismiss
import com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.restaureable_action.RestoreableAction.ActionType.OPEN_SETTINGS
import com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.restaureable_action.RestoreableAction.ActionType.OPEN_PLAYSTORE
import com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.restaureable_action.RestoreableAction.ActionType.DISMISS
import com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.restaureable_action.RestoreableAction.ActionType.NONE
import com.example.desafioscomunsandroidcustoms.util.openAppSettings
import com.example.desafioscomunsandroidcustoms.util.openPlayStore
import com.example.desafioscomunsandroidcustoms.util.setVisible
import com.example.desafioscomunsandroidcustoms.util.viewBinding

// 1) como criar um dialog que retem os listeners (quando isso pode ocorrer?)
// - troca de tema de dia para noite
// - troca de permissão dinamicamente
// - rotação do aparelho
// - mudança de configurações (idioma, etc.)
//
// 2) como recriar os listeners
// 3) como usar isso na prática

class RestoreableActionDialog(
    val title: String = "",
    val message: String = "",
    val positiveLabel: String = "",
    val positiveAction: () -> Unit = {},
    val negativeLabel: String? = null,
    val negativeAction: () -> Unit = {},
    val dismissAction: () -> Unit = {},
    val restorableActions: List<RestoreableAction> = emptyList(),
) : DialogFragment(R.layout.fragment_restorable_action_dialog) {

    private var _dismissAction: () -> Unit = dismissAction
    private val binding by viewBinding(FragmentRestorableActionDialogBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            header.text = title
            body.text = message
            positiveButton.text = positiveLabel
            positiveButton.setOnClickListener {
                dismiss()
                positiveAction()
            }
            negativeButton.setVisible(negativeLabel != null)
            negativeButton.text = negativeLabel
            negativeButton.setOnClickListener {
                dismiss()
                negativeAction()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBundle(DIALOG, Bundle().apply {
            putString(TITLE, title)
            putString(MESSAGE, message)
            putString(POSITIVE_LABEL, positiveLabel)
            if(binding.negativeButton.isVisible) {
                putString(NEGATIVE_LABEL, negativeLabel)
            }
            restorableActions.forEach { action ->
                when (action) {
                    is Positive -> putString(POSITIVE_ACTION, action.type.name)
                    is Negative -> putString(NEGATIVE_ACTION, action.type.name)
                    is Dismiss -> putString(DISMISS_ACTION, action.type.name)
                }
            }
        })
        super.onSaveInstanceState(outState)
    }

    /**
     * Everytime the user changes the theme while this dialog is showing,
     * we need to re-store/re-assign its previous values and actions
     */
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        savedInstanceState?.getBundle(DIALOG)?.let {
            with(binding) {
                header.text = "Erro desconhecido"
                body.text = "Se o erro persistir entre em contacto com o nosso suporte"
                positiveButton.text = it.getString(POSITIVE_LABEL, getString(R.string.ok))
                negativeButton.text = it.getString(NEGATIVE_LABEL, null)
                if(negativeButton.text != null){
                    negativeButton.setVisible(true)
                }
                restoreAction(positiveButton, it.getString(POSITIVE_ACTION, NONE_ACTION))
                restoreAction(negativeButton, it.getString(NEGATIVE_ACTION, NONE_ACTION))
                restoreDismissAction(it.getString(DISMISS_ACTION, NONE_ACTION))
            }
        }
        super.onViewStateRestored(savedInstanceState)
    }

    companion object Restorable {
        const val DIALOG = "dialog"
        const val TITLE = "title"
        const val MESSAGE = "message"
        const val POSITIVE_LABEL = "positive_label"
        const val NEGATIVE_LABEL = "negative_label"
        const val POSITIVE_ACTION = "positive_action"
        const val NEGATIVE_ACTION = "negative_action"
        const val DISMISS_ACTION = "dismiss_action"
        const val NONE_ACTION = "none_action"
    }

    private fun restoreAction(button: MaterialButton, actionString: String) {
        when (RestoreableAction.ActionType.getType(actionString)) {
            OPEN_SETTINGS -> button.setOnClickListener {
                openAppSettings()
            }
            OPEN_PLAYSTORE -> button.setOnClickListener {
                openPlayStore()
            }
            DISMISS -> button.setOnClickListener { dismiss() }
            NONE -> Unit
        }
    }

    private fun restoreDismissAction(actionString: String) {
        _dismissAction = when (RestoreableAction.ActionType.getType(actionString)) {
            DISMISS -> { { findNavController().navigateUp() } }
            else -> { {} }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        _dismissAction()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): android.app.Dialog {
        setStyle(STYLE_NO_FRAME, R.style.Theme_DesafiosComunsAndroidCustoms)
        return super.onCreateDialog(savedInstanceState)
    }
}