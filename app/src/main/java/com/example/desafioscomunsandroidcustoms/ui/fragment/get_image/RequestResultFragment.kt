package com.example.desafioscomunsandroidcustoms.ui.fragment.get_image

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.databinding.FragmentRequestResultBinding
import com.example.desafioscomunsandroidcustoms.util.navTo
import com.example.desafioscomunsandroidcustoms.util.shouldRequestPermission
import com.example.desafioscomunsandroidcustoms.ui.fragment.get_image.ResultFragment.Companion.KEY_SELECTED_IMAGE_URI
import com.example.desafioscomunsandroidcustoms.util.toast
import com.example.desafioscomunsandroidcustoms.util.viewBinding

class RequestResultFragment : Fragment(R.layout.fragment_request_result) {

    private val binding by viewBinding(FragmentRequestResultBinding::bind)

    private lateinit var fileChooserLauncher: ActivityResultLauncher<String>

    private lateinit var requestPermissionsForFileChooser: ActivityResultLauncher<Array<String>>
    private val fileChooserPermissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fileChooserLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                uri?.let {
                    navTo(
                        R.id.resultFragment,
                        bundleOf(Pair(KEY_SELECTED_IMAGE_URI, uri.toString()))
                    )
                }
                // CASO ELE NÃO NAVEGUE, ELE RETORNA PARA TELA QUE LANCOU A SOLICITACÃO
            }

        requestPermissionsForFileChooser = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionResult ->
            val permissionsIdentified = permissionResult.all { it.key in fileChooserPermissions }
            val permissionsGrant = permissionResult.all { it.value == true }
            if (permissionsIdentified && permissionsGrant) {
                fileChooserLauncher.launch("image/*")
            } else {
                val deniedPermissions = permissionResult.map { perm ->
                    if (perm.value == false) perm.key else ""
                }.filter { it.isNotEmpty() }.toList()
                showWhyPermissionsAreNeeded(deniedPermissions)
            }
        }
    }

    private fun showWhyPermissionsAreNeeded(deniedPermissions: List<String>) {
        val msg = StringBuilder()
        msg.append("READ_EXTERNAL_STORAGE \n")
        msg.append("WRITE_EXTERNAL_STORAGE \n")
        toast("Você precisa nos conceder as permissões:\n$msg")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentFragmentManager.setFragmentResultListener(
            KEY_CHOOSER_REQUEST,
            viewLifecycleOwner
        ) { requestKey, result ->
            toast(requestKey)
            toast(result.getString(KEY_CHOOSER_BACK_BUTTON) ?: "")
        }

        binding.requestResultButton.setOnClickListener {
            if (shouldRequestPermission(fileChooserPermissions)) {
                requestPermissionsForFileChooser.launch(fileChooserPermissions)
            } else {
                fileChooserLauncher.launch("image/*")
            }
        }
    }

    companion object {
        const val KEY_CHOOSER_REQUEST = "key_chooser_result"
        const val KEY_CHOOSER_BACK_BUTTON = "key_chooser_window"
    }
}