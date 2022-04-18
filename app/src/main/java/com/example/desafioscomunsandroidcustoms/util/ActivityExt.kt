package com.example.desafioscomunsandroidcustoms.util

import android.app.Activity
import android.view.WindowManager

/** IMPEDIR AS CAPTURAS DE TELAS E QUANDO O APP FICA EM BACK GROUND COM A TELA BRANCA */
fun Activity.preventScreenshotsAndRecentAppThumbnails(){
    window.setFlags(
        WindowManager.LayoutParams.FLAG_SECURE,
        WindowManager.LayoutParams.FLAG_SECURE
    )
}