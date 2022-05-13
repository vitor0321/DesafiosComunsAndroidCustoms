package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.itens_decorativos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.databinding.FragmentItemDecoratorBinding
import com.example.desafioscomunsandroidcustoms.presentation.ui.recyclerview.CustomMainMenuItemAdapter
import com.example.desafioscomunsandroidcustoms.util.attachHeader
import com.example.desafioscomunsandroidcustoms.util.setCustomHeadlineDivider
import com.example.desafioscomunsandroidcustoms.util.setSimpleDefaultDivider
import com.example.desafioscomunsandroidcustoms.util.toast
import com.example.desafioscomunsandroidcustoms.util.viewBinding


// 0) Fragment que vai exibir nossa lista (recyclerView)
// 1) Criar uma divisória simples com um drawable (divider)
// 2) Criar divisórias de texto entre itens da lista (text-divider)
// 3) Criar um Header para o topo da lista
// 4) Te dá pontos de partida com implementações referências (CustomItemDecorator)
// 5) Criar extensões bacanas pra facilitar tua vida (mão na roda!!!)

class ItemDecoratorFragment : Fragment(R.layout.fragment_item_decorator), View.OnClickListener {

    // VEJA TBM VIDEO COMO DELEGAR VIEW BINDING
    private val binding by viewBinding(FragmentItemDecoratorBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            val items = mutableListOf(
                Pair("titulo1","button1"),
                Pair("titulo2","button2"),
                Pair("titulo3","button3"),
                Pair("titulo4","button4"),
                Pair("titulo5","button5"),
                Pair("titulo6","button6"),
                Pair("titulo7","button7"),
                Pair("titulo8","button8"),
                Pair("titulo9","button9"),
                Pair("titulo2","button10"),
                Pair("titulo3","button11"),
                Pair("titulo4","button12"),
                Pair("titulo5","button13"),
                Pair("titulo6","button14"),
                Pair("titulo7","button15"),
                Pair("titulo8","button16"),
                Pair("titulo9","button17")
            )
            val customAdapter = CustomMainMenuItemAdapter(requireContext(), items,this@ItemDecoratorFragment)
            itemDecoratorRecylerview.adapter = customAdapter
            // VOCÊ PODE COMBINAR DIVERSOS DECORADORES
            itemDecoratorRecylerview.attachHeader("Header Personalizado No Topo")
            itemDecoratorRecylerview.setCustomHeadlineDivider()
            itemDecoratorRecylerview.setSimpleDefaultDivider(R.drawable.item_decorator_divider)

            // Sticky Header
            // https://medium.com/android-news/the-beauty-of-a-sticky-itemdecoration-db18171f5e26
        }
    }

    override fun onClick(v: View?) {
        toast("Já te inscrevesse no canal?")
    }
}