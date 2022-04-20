package com.example.desafioscomunsandroidcustoms.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkCapabilities.NET_CAPABILITY_VALIDATED
import android.os.*
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_FORCED
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import timber.log.Timber
import javax.crypto.Cipher

/** VERIFICA SE A PERMISSÃO FOI CONCEDIDA */
fun Fragment.hasPermission(permission: String): Boolean {
    val permissionCheckResult = ContextCompat.checkSelfPermission(requireContext(), permission)
    return PackageManager.PERMISSION_GRANTED == permissionCheckResult
}

// VERIFICA SE DEVE SOLICITAR AS PERMISSÕES NOVAMENTE
fun Fragment.shouldRequestPermission(permissions: Array<String>): Boolean {
    val grantedPermissions = mutableListOf<Boolean>()
    permissions.forEach { permission ->
        grantedPermissions.add(hasPermission(permission))
    }
    return grantedPermissions.any { granted -> !granted }
}

/** EXIBE UMA MENSAGEM SIMPLES TEMPORARIZADA NA TELA DO CELULAR */
fun Fragment.toast(msg: String) = Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()

/** NAVEGA PARA O DESTINO INDICADO ATRAVES DO ID DO NAGIVATION GRAPH */
fun Fragment.navTo(@IdRes dest: Int) = findNavController().navigate(dest)
fun Fragment.navTo(directions: NavDirections) = findNavController().navigate(directions)
fun Fragment.navTo(@IdRes dest: Int, args: Bundle) = findNavController().navigate(dest, args)
fun Fragment.navBack() = findNavController().navigateUp()

/** ESCONDER O TECLADO */
fun Fragment.hideKeyboard(view: View? = activity?.window?.decorView?.rootView) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        view?.hideKeyboard(view)
    } else {
        inputMethodManager()?.hideSoftInputFromWindow(view?.applicationWindowToken, 0)
    }
}

fun Fragment.inputMethodManager() =
    context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager

/** EXIBIR O TECLADO */
fun Fragment.showKeyboard(view: View? = activity?.currentFocus) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        view?.showKeyboard(view)
    } else {
        view?.let {
            it.postDelayed({
                it.requestFocus()
                inputMethodManager()?.showSoftInput(it, SHOW_FORCED)
            }, 100)
        }
    }
}

/** VIBRAR CELULAR */
@SuppressLint("ObsoleteSdkInt")
@Suppress("DEPRECATION")
fun Fragment.vibrante(duration: Long = 100) {

    val vibrator = requireContext().getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator

    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val vm =
                requireContext().getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vm.defaultVibrator.vibrate(
                VibrationEffect.createOneShot(
                    duration,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && Build.VERSION.SDK_INT < Build.VERSION_CODES.S -> {
            vibrator?.vibrate(
                VibrationEffect.createOneShot(
                    duration,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        }
        else -> vibrator?.vibrate(duration)
    }
}


//VERIFICAR SE TEM INTERNET
fun Fragment.hasInternet(): Boolean {
    val connMgr =
        requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities = connMgr.getNetworkCapabilities(connMgr.activeNetwork)
        capabilities != null &&
                //VERIFICAR SE TEM REDE WIFI ETC.
                capabilities.hasCapability(NET_CAPABILITY_INTERNET) &&
                //VERIFICAR SE TEM CONNEXÃO POIS EM ALGUNS CASOS TEM O WIFI LIGADO MAS
                //NÃO TEM NET, COMO EM UM AEROPORTO
                capabilities.hasCapability(NET_CAPABILITY_VALIDATED)
    } else {
        @Suppress("DEPRECATION")
        connMgr.activeNetworkInfo?.isConnected == true
    }
}

//ATIVAR BIOMETRIA API>=23
/** EXIBE UM LEITOR DE BIOMETRIA: XXXXXXX */
fun Fragment.promptBiometricChecker(
    title: String,
    message: String? = null, // OPCIONAL - SE QUISER EXIBIR UMA MENSAGEM
    negativeLabel: String,
    confirmationRequired: Boolean = true,
    initializedCipher: Cipher? = null, // OPICIONAL - SE VC MESMO(SUA APP) QUISER MANTER O CONTROLE SOBRE OS ACESSOS
    onAuthenticationSuccess: (BiometricPrompt.AuthenticationResult) -> Unit,
    onAuthenticationError: (Int, String) -> Unit
) {
    val executor = ContextCompat.getMainExecutor(requireContext())
    val prompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            Timber.d("Authenticado com sucesso, acesso permitido!")
            onAuthenticationSuccess(result)
        }

        override fun onAuthenticationError(errorCode: Int, errorMessage: CharSequence) {
            Timber.d("Acesso negado! Alguem ta tentando usar teu celular!")
            onAuthenticationError(errorCode, errorMessage.toString())
        }
    })

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle(title)
        .apply { if (message != null) setDescription(message) }
        .setConfirmationRequired(confirmationRequired)
        .setNegativeButtonText(negativeLabel)
        .build()

    initializedCipher?.let {
        val cryptoObject = BiometricPrompt.CryptoObject(initializedCipher)
        prompt.authenticate(promptInfo, cryptoObject)
        return
    }

    prompt.authenticate(promptInfo)
}

