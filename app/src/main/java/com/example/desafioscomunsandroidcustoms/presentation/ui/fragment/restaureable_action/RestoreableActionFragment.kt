package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.restaureable_action

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.databinding.FragmentRestoreableActionBinding
import com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.restaureable_action.RestoreableAction.*
import com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.restaureable_action.RestoreableAction.ActionType.*
import com.example.desafioscomunsandroidcustoms.util.openAppSettings
import com.example.desafioscomunsandroidcustoms.util.openPlayStore
import com.example.desafioscomunsandroidcustoms.util.viewBinding

// 1) como criar um dialog que retem os listeners (quando isso pode ocorrer?)
// - troca de tema de dia para noite
// - troca de permissão dinamicamente
// - rotação do aparelho
// - mudança de configurações (idioma, etc.)
//
// 2) como recriar os listeners
// 3) como usar isso na prática

class RestoreableActionFragment : Fragment(R.layout.fragment_restoreable_action) {

    // VEJA TBM VIDEO COMO DELEGAR VIEW BINDING
    private val binding by viewBinding(FragmentRestoreableActionBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.showDialog.setOnClickListener {
            RestoreableActionDialog(
                title = "Retendo Ações",
                message = "Altere o tema da aplicação de dia para noite, retorne para esta tela. Pressione então o botão: Abrir Configurações ou Baixar da Playstore",
                positiveLabel = "Abrir Configurações",
                positiveAction = { openAppSettings() },
                negativeLabel = "Abrir na Playstore",
                negativeAction = { openPlayStore() },
                dismissAction = {},
                restorableActions = listOf(
                    Positive(OPEN_SETTINGS),
                    Negative(OPEN_PLAYSTORE),
                    Dismiss(NONE)
                )
            ).show(parentFragmentManager, RestoreableActionFragment::class.java.simpleName)
        }
    }
}