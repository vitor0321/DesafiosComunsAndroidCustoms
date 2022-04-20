package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.check_internet

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.databinding.FragmentHasInternetBinding
import com.example.desafioscomunsandroidcustoms.util.hasInternet
import com.example.desafioscomunsandroidcustoms.util.viewBinding


class HasInternetFragment : Fragment(R.layout.fragment_has_internet) {

    private val binding by viewBinding(FragmentHasInternetBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (hasInternet()) {
            true -> binding.textViewNet.text = "TEM INTERNET!"
            false -> binding.textViewNet.text = "OPS! NÃO TEM INTERNET!"
        }
    }
}