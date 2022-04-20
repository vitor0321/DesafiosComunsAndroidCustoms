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
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.full_screen.FullscreenAlertDialog
import com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.requisicao_api.ClearableCoroutineScope
import com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.requisicao_api.CoroutineContextProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

/** EXIBIR UM ALERTA DE MENSAGENS FULLSCREEN PERSONALIZADO */
fun Fragment.showFullscreenAlertDialog(
    title: String,
    message: String,
    positiveButtonLabel: String = getString(android.R.string.ok),
    positiveButtonClickListener: () -> Unit = {},
    cancelButtonLabel: String? = null,
    negativeButtonClickListener: () -> Unit = {},
    dismissAction: () -> Unit = {},
) = FullscreenAlertDialog(
    title = title,
    message = message,
    positiveLabel = positiveButtonLabel,
    positiveAction = positiveButtonClickListener,
    cancelLabel = cancelButtonLabel,
    cancelAction = negativeButtonClickListener,
    dismissAction = dismissAction,
).also { it.show(parentFragmentManager, it.javaClass.simpleName) }

/** EXIBIR MATERIAL ALERT DIALOG DE ACORDO COM SUAS NECESSIDADES */
fun Fragment.showDefaultMaterialAlertDialog(
    title: String? = null,
    message: String? = null,
    positiveButtonLabel: String? = null,
    positiveButtonClickListener: () -> Unit = {},
    negativeButtonLabel: String? = null,
    negativeButtonClickListener: () -> Unit = {},
    cancelable: Boolean = false,
    cancelListener: () -> Unit = {},
    dismissListener: () -> Unit = {},
) {
    MaterialAlertDialogBuilder(requireContext())
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveButtonLabel) { dialog, _ -> dialog.dismiss(); positiveButtonClickListener() }
        .setNegativeButton(negativeButtonLabel) { dialog, _ -> dialog.dismiss(); negativeButtonClickListener() }
        .setCancelable(cancelable)
        .setOnCancelListener { cancelListener() }
        .setOnDismissListener { dismissListener() }
        .create().also { it.show() }
}

/** CRIAR ALERTAS SEMI-CUSTOMIZAOS (APENAS COM CONTEUDO ESTÁTICO CUSTOMIZADO) */
fun Fragment.showDefaultMaterialAlertDialogWithCustomStaticContent(
    positiveButtonLabel: String? = null,
    positiveButtonClickListener: () -> Unit = {},
    negativeButtonLabel: String? = null,
    negativeButtonClickListener: () -> Unit = {},
    cancelable: Boolean = false,
    cancelListener: () -> Unit = {},
    dismissListener: () -> Unit = {},
    @LayoutRes customLayoutId: Int,
    @StyleRes styleId: Int? = null,
    @DrawableRes customBackgroundId: Int? = null
) {
    // if you want to customize the dialog's theme
    val builder = if (styleId != null) MaterialAlertDialogBuilder(
        ContextThemeWrapper(
            requireContext(),
            styleId
        )
    ) else MaterialAlertDialogBuilder(requireContext())

    // if you want to customize the alert dialog's content!
    builder.setView(customLayoutId)

    val dialog = builder
        .setPositiveButton(positiveButtonLabel) { dialog, _ -> dialog.dismiss(); positiveButtonClickListener() }
        .setNegativeButton(negativeButtonLabel) { dialog, _ -> dialog.dismiss(); negativeButtonClickListener() }
        .setCancelable(cancelable)
        .setOnCancelListener { cancelListener() }
        .setOnDismissListener { dismissListener() }
        .create()

    // if you want to customize the window background like color and border
    if (customBackgroundId != null) {
        dialog.window?.setBackgroundDrawableResource(customBackgroundId)
    }
    dialog.show()
}

/** CRIAR ALERTAS TOTALMENTE CUSTOMIZADOS */
fun Fragment.createFullCustomAlertDialog(
    customView: View,
    @StyleRes styleId: Int? = null,
    @DrawableRes customBackgroundId: Int? = null
): AlertDialog {
    // if you want to customize the dialog's theme
    val builder = if (styleId != null) MaterialAlertDialogBuilder(
        ContextThemeWrapper(
            requireContext(),
            styleId
        )
    ) else MaterialAlertDialogBuilder(requireContext())

    builder.setView(customView)
    val dialog = builder.create()

    // if you want to customize the window background like color and border
    if (customBackgroundId != null) {
        dialog.window?.setBackgroundDrawableResource(customBackgroundId)
    }
    return dialog
}

/** REALIZAR UMA TAREFA EM LOOP (EX: CONSULTAR ALGUM RESULTADO DE UM SERVIDOR) */
fun Fragment.polling(
    isOffline: () -> Boolean = { false },
    onOffline: () -> Unit = {},
    isCompleted: () -> Boolean = { false },
    onCompleted: () -> Unit = {},
    isError: () -> Boolean = { false },
    onError: () -> Unit = {},
    isCanceled: () -> Boolean = { false },
    onCanceled: () -> Unit = {},
    pollingDelayInMilliSeconds: Long = 5000L
) {
    val pollingScope = ClearableCoroutineScope(CoroutineContextProvider().ui)
    pollingScope.launch {
        if (isOffline()) {
            handleStateChange(onOffline, pollingScope)
            return@launch
        }
        when {
            isCompleted() -> {
                handleStateChange(onCompleted, pollingScope)
                return@launch
            }
            isCanceled() -> {
                handleStateChange(onCanceled, pollingScope)
                return@launch
            }
            isError() -> {
                handleStateChange(onError, pollingScope)
                return@launch
            }
            else -> {
                toast("polling a cada 5 segundos!")
                delay(pollingDelayInMilliSeconds)
                pollingScope.clearScope()
                polling(
                    isOffline = isOffline,
                    onOffline = onOffline,
                    isCompleted = isCompleted,
                    onCompleted = onCompleted,
                    isError = isError,
                    onError = onError,
                    isCanceled = isCanceled,
                    onCanceled = onCanceled
                )
            }
        }
    }
}
private fun handleStateChange(
    handle: () -> Unit, pollingScope: ClearableCoroutineScope
) {
    handle()
    pollingScope.clearScope()
    return
}

