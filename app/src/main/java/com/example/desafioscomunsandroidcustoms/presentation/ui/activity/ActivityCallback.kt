package com.example.desafioscomunsandroidcustoms.presentation.ui.activity

interface ActivityCallback {

    fun showAppBarBackButton(show: Boolean)
    fun showAppBarTitle(show: Boolean)
    fun showActionBar(show: Boolean)
    fun showProgressBar()
    fun hideProgressBar()
}