package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.databinding.FragmentMainBinding
import com.example.desafioscomunsandroidcustoms.presentation.ui.activity.bloquear_captura_tela.BlockScreenShotsActivity
import com.example.desafioscomunsandroidcustoms.util.navTo
import com.example.desafioscomunsandroidcustoms.util.viewBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            requestPermission.setOnClickListener { navTo(R.id.action_mainFragment_to_requestPermissionFragment) }

            startForResults.setOnClickListener { navTo(R.id.action_mainFragment_to_requestResultFragment) }

            mostarEsconderTeclado.setOnClickListener { navTo(R.id.action_mainFragment_to_tecladoFragment) }

            vibrarFragmentButton.setOnClickListener { navTo(R.id.action_mainFragment_to_vibrarFragment) }

            blockScreenShotsButton.setOnClickListener {
                val intent = Intent(activity, BlockScreenShotsActivity::class.java)
                activity?.startActivity(intent)
            }

            checkIntertButton.setOnClickListener { navTo(R.id.action_mainFragment_to_hasInternetFragment) }

            webViewDarkButton.setOnClickListener { navTo(R.id.action_mainFragment_to_darkWebViewFragment) }

            checkEditTextButton.setOnClickListener { navTo(R.id.action_mainFragment_to_checkEditTextFragment) }

            checkEmulatorButton.setOnClickListener { navTo(R.id.action_mainFragment_to_checkIfEmulatorFragment) }

            checkBackButton.setOnClickListener { navTo(R.id.action_mainFragment_to_backPressedFragment) }

            checkBiometryButton.setOnClickListener { navTo(R.id.action_mainFragment_to_biometryFragment) }

            fullscreenButton.setOnClickListener { navTo(R.id.action_mainFragment_to_fullscreenDialogFragment) }

            alertButton.setOnClickListener { navTo(R.id.action_mainFragment_to_customDialogFragment) }

            pollingButton.setOnClickListener { navTo(R.id.action_mainFragment_to_pollingApiFragment) }

            bottomSheetButton.setOnClickListener { navTo(R.id.action_mainFragment_to_bottomSheetDialogFragment) }

            mvpButton.setOnClickListener { navTo(R.id.action_mainFragment_to_myViewFragment) }

            recyclerviewCustumizadoButton.setOnClickListener { navTo(R.id.action_mainFragment_to_itemDecoratorFragment) }
        }
    }
}