package com.example.desafioscomunsandroidcustoms.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkCapabilities.NET_CAPABILITY_VALIDATED
import android.os.*
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_FORCED
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.desafioscomunsandroidcustoms.util.const.NUMBERS
import com.example.desafioscomunsandroidcustoms.util.const.PASSWORD_REGEX
import com.example.desafioscomunsandroidcustoms.util.const.SPECIAL_CHARACTERS_REGEX
import java.util.regex.Matcher
import java.util.regex.Pattern

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

//VERIFICAR SE O EDITTEXT DE EMAIL
fun Fragment.checkEmail(email: String): Boolean {
    return when {
        email.isBlank() -> false
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> false
        email.length >= 20 -> false
        else -> true
    }
}

fun Fragment.hasSpecialCharacters(password: String) = Pattern.matches(SPECIAL_CHARACTERS_REGEX, password)

fun Fragment.hasNumber(password: String) = Pattern.matches(NUMBERS, password)

object const  {
    const val SPECIAL_CHARACTERS_REGEX =
        "(?=.*[\\u0020-\\u002F\\u003A-\\u0040\\u005B-\\u0060\\u007B-\\u007E])"
    const val PASSWORD_REGEX =
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])($SPECIAL_CHARACTERS_REGEX).{8,}\$"
    const val NUMBERS = "(?=.*[0-9])"
}
