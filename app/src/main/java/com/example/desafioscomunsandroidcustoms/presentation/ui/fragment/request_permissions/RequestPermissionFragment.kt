package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.request_permissions

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.databinding.FragmentRequestPermissionBinding
import com.example.desafioscomunsandroidcustoms.util.shouldRequestPermission
import com.example.desafioscomunsandroidcustoms.util.toast
import com.example.desafioscomunsandroidcustoms.util.viewBinding

class RequestPermissionFragment : Fragment(R.layout.fragment_request_permission) {

    private val binding by viewBinding(FragmentRequestPermissionBinding::bind)

    //NOVA API DE SOLICITAÇAO DE PERMISSÕES QUE USA CALLBACKS
    private lateinit var fileChooserPermissionLauncher: ActivityResultLauncher<Array<String>>

    //PERMISSÕES QUE IREMOS SOLICITAR AO USUÁRIO EM TEMLPO DE EXECUÇÃO
    private val fileChooserPermissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fileChooserPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionResult ->
            val permissionsIdentified = permissionResult.all { it.key in fileChooserPermissions }
            val permissionsGrant = permissionResult.all { it.value == true }
            if (permissionsIdentified && permissionsGrant) {
                toast("Permissões concedidas! Parabens!")
            } else {
                val deniedPermissions = permissionResult.map { perm ->
                    if (perm.value == false) perm.key else ""
                }.filter { it.isNotEmpty() }.toList()
                //EXPLICA AO USUÁRIO, PORQUE ELE PRECISA CONCEDER AS PERMISSÕES
                showWhyPermissionsAreNeeded(deniedPermissions)
            }
        }
    }

    //EXPLICA AO USUÁRIO, PORQUE ELE PRECISA CONCEDER AS PERMISSÕES
    private fun showWhyPermissionsAreNeeded(deniedPermission: List<String>) {
        val msg = StringBuilder()
        msg.append("READ_EXTERNAL_STORAGE \n")
        msg.append("WRITE_EXTERNAL_PERMISSION \n")
        toast("Você precisa nos conceder as permissões: \n$msg")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.requestPermissionButton.setOnClickListener {
            if (shouldRequestPermission(fileChooserPermissions)) {
                fileChooserPermissionLauncher.launch(fileChooserPermissions)
            } else {
                toast("Permissões já foram dadas! Parabens!")
                // DA SUBSCRIBE PARA NÃO PERDER A PRÓXIMA AULA DE REQUEST RESULT! :)
            }
        }
    }
}