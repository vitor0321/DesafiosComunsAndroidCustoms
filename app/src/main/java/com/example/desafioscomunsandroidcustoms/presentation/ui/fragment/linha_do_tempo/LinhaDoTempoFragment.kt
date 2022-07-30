package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.linha_do_tempo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.databinding.FragmentLinhaDoTempoBinding
import com.example.desafioscomunsandroidcustoms.util.viewBinding

// 1) COMO CRIAR UM LAYOUT DE LINHA DO TEMPO
// 2) COMO ESTRUTURAR OS COMPONENTES
// 3) COMO JUNTAR TUDO E USA-LOS NA PRÁTICA

class LinhaDoTempoFragment : Fragment(R.layout.fragment_linha_do_tempo) {

    // VEJA VIDEO BINDING AUTOMATICO: https://youtu.be/qivrch6qxQw
    private val binding by viewBinding(FragmentLinhaDoTempoBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.xIdBusConnectStartInclude.xIdBusStartSubtitle.text = "Rua Tupan"

        binding.xIdBusConnectInfoInclude.xIdBusInfoBadge.text = "201"
        binding.xIdBusConnectInfoInclude.xIdBusInfoTitle.text = "Presidente Kennedy"
        binding.xIdBusConnectInfoInclude.xIdBusInfoSubtitle.text = "Via: Av. João Gomes Freire"
        binding.xIdBusConnectInfoInclude.xIdBusInfoTariff.text = "R$ 3.45"

        binding.xIdBusConnectScalaInclude.xIdBusScalaInfoSubtitle.text = "Rua Pedro Alvares Cabral"

        binding.xIdBusConnectInfoScalaInclude.xIdBusInfoBadge.text = "403"
        binding.xIdBusConnectInfoScalaInclude.xIdBusInfoTitle.text = "Cais do Apolo"
        binding.xIdBusConnectInfoScalaInclude.xIdBusInfoSubtitle.text = "Via: Av. Agamenon Magalhães"
        binding.xIdBusConnectInfoScalaInclude.xIdBusInfoTariff.text = "R$ 2.25"

        binding.xIdBusConnectEndInclude.xIdBusEndSubtitle.text = "Av. Coselheiro Aguiar"

    }
}