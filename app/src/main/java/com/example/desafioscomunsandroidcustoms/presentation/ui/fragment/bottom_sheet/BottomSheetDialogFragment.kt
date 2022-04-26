package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.bottom_sheet

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.databinding.FragmentBottomSheetDialogBinding
import com.example.desafioscomunsandroidcustoms.util.viewBinding

class BottomSheetDialogFragment : Fragment(R.layout.fragment_bottom_sheet_dialog) {

    private val binding by viewBinding(FragmentBottomSheetDialogBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pressBottomSheetBtn.setOnClickListener {
            TicketSelectionFragment(true).show(
                parentFragmentManager,
                BottomSheetDialogFragment::class.simpleName
            )
        }
    }
}