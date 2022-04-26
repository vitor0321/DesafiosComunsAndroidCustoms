package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.databinding.FragmentTicketSelectionBinding
import com.example.desafioscomunsandroidcustoms.util.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TicketSelectionFragment(private val showAddItem: Boolean) : BottomSheetDialogFragment(),
    OnItemClickListener<TicketSelectionElement> {

    private val binding by viewBinding(FragmentTicketSelectionBinding::bind)

    private val bottomSheetElement = ArrayList<TicketSelectionElement>()
    private lateinit var ticketAdapter: TicketSelectionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ticket_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mockSomeTickets()

        val recyclerView = binding.tickets
        ticketAdapter = TicketSelectionAdapter(bottomSheetElement, this)
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = ticketAdapter
    }

    override fun onItemClicked(item: TicketSelectionElement) {
        when (item.type) {
            TicketSelectionType.SELECT -> onTicketSelected(item.title)
            TicketSelectionType.ADD -> onAdd()
            TicketSelectionType.ENTER_MANUAL -> onEnterManual()
        }
        dismiss()
    }

    private fun onTicketSelected(ticketNumber: String) {
        toast("Ticket selecionado: $ticketNumber")
    }

    private fun onAdd() {
        toast("Adicionar")
    }

    private fun onEnterManual() {
        toast("Adicionar manualmente")
    }

    private fun mockSomeTickets() {
        bottomSheetElement.add(
            TicketSelectionElement(
                R.drawable.ic_error,
                "Ticket Expirado",
                true,
                TicketSelectionType.SELECT
            )
        )

        bottomSheetElement.add(
            TicketSelectionElement(
                R.drawable.ic_success,
                "Ticket Valido",
                false,
                TicketSelectionType.SELECT
            )
        )

        bottomSheetElement.add(
            TicketSelectionElement(
                R.drawable.ic_success,
                "Ticket Valido",
                false,
                TicketSelectionType.ENTER_MANUAL
            )
        )
    }

}