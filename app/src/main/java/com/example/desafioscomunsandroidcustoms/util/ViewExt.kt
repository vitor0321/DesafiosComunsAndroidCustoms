package com.example.desafioscomunsandroidcustoms.util

import android.animation.ObjectAnimator
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.animation.BounceInterpolator
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import com.google.android.material.textfield.TextInputLayout


@RequiresApi(Build.VERSION_CODES.R)
        /** ESCONDE O TECLADO */
fun View.showKeyboard(view: View) {
    windowInsetsController?.show(WindowInsets.Type.ime())
    view.requestFocus()
}

@RequiresApi(Build.VERSION_CODES.R)
        /** EXIBE O TECLADO */
fun View.hideKeyboard(view: View) {
    windowInsetsController?.hide(WindowInsets.Type.ime())
    view.clearFocus()
}

//ANIMAÇÃO EM UM TEXTINPUTLAYOUT
fun TextInputLayout.shake(onEndAction: () -> Unit = {}) {
    val startX = 0f
    val translationX = 35f
    val bounceDuration = 700L

    ObjectAnimator.ofFloat(
        this,
        "translationX",
        startX,
        translationX,
        startX
    ).apply {
        interpolator = BounceInterpolator()
        duration = bounceDuration
        start()
    }.doOnEnd { onEndAction() }
}

