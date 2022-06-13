package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.endpoints

object RequestBody {

    data class LoginBody(
        val username: String,
        val password: String
    )
}