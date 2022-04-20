package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.presentation.ui.activity.bloquear_captura_tela.BlockScreenShotsActivity
import com.example.desafioscomunsandroidcustoms.databinding.FragmentMainBinding
import com.example.desafioscomunsandroidcustoms.util.navTo
import com.example.desafioscomunsandroidcustoms.util.viewBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            requestPermission.setOnClickListener { navTo(R.id.requestPermissionFragment) }

            startForResults.setOnClickListener { navTo(R.id.requestResultFragment) }

            mostarEsconderTeclado.setOnClickListener { navTo(R.id.tecladoFragment) }

            vibrarFragmentButton.setOnClickListener { navTo(R.id.vibrarFragment) }

            blockScreenShotsButton.setOnClickListener {
                val intent = Intent(activity, BlockScreenShotsActivity::class.java)
                activity?.startActivity(intent)
            }

            checkIntertButton.setOnClickListener { navTo(R.id.hasInternetFragment) }

            webViewDarkButton.setOnClickListener { navTo(R.id.darkWebViewFragment) }

            checkEditTextButton.setOnClickListener { navTo(R.id.checkEditTextFragment) }

            checkEmulatorButton.setOnClickListener { navTo(R.id.checkIfEmulatorFragment) }

            checkBackButton.setOnClickListener { navTo(R.id.backPressedFragment) }

            checkBiometryButton.setOnClickListener { navTo(R.id.biometryFragment) }

            fullscreenButton.setOnClickListener { navTo(R.id.fullscreenDialogFragment) }

            alertButton.setOnClickListener { navTo(R.id.customDialogFragment) }
        }
    }
}