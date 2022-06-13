package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.endpoints

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("accessToken")
    val token: String,

    @SerializedName("tokenType")
    val tokenType: String
)