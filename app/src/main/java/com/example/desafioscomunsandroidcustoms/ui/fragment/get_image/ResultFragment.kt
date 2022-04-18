package com.example.desafioscomunsandroidcustoms.ui.fragment.get_image

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.databinding.FragmentResultBinding
import com.example.desafioscomunsandroidcustoms.util.navBack
import com.example.desafioscomunsandroidcustoms.ui.fragment.get_image.RequestResultFragment.Companion.KEY_CHOOSER_BACK_BUTTON
import com.example.desafioscomunsandroidcustoms.ui.fragment.get_image.RequestResultFragment.Companion.KEY_CHOOSER_REQUEST
import com.example.desafioscomunsandroidcustoms.util.viewBinding

class ResultFragment : Fragment(R.layout.fragment_result) {

    private val binding by viewBinding(FragmentResultBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageUriExtra = arguments?.getString(KEY_SELECTED_IMAGE_URI)

        Glide.with(this).load(imageUriExtra).into(binding.imageView)

        binding.navBack.setOnClickListener {
            requireFragmentManager().setFragmentResult(
                    KEY_CHOOSER_REQUEST,
                    bundleOf(KEY_CHOOSER_BACK_BUTTON to "PRESSIONEI VOLTAR")
                )
            navBack()
        }
    }

    companion object {
        const val KEY_SELECTED_IMAGE_URI = "KEY_SELECTED_IMAGE_URI"
    }
}