package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.mvp.concrete_view

import android.os.Bundle
import android.view.View
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.databinding.FragmentMyViewBinding
import com.example.desafioscomunsandroidcustoms.util.hideKeyboard
import com.example.desafioscomunsandroidcustoms.util.toast
import com.example.desafioscomunsandroidcustoms.util.viewBinding
import com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.mvp.base_alert.AlertType
import com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.mvp.base_contract.PresenterContract
import com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.mvp.base_contract.ViewContract
import com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.mvp.base_view.BaseFragmentView
import com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.mvp.concrete_contract.MyViewContract
import com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.mvp.concrete_presenter.MyViewPresenter
import com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.mvp.navigation.ScreenDestination

// PRESTA A ATENCÃO EM: MyViewContract.Presenter E MyViewContract.View
class MyViewFragment : BaseFragmentView<MyViewContract.Presenter>(R.layout.fragment_my_view),
    MyViewContract.View {

    private val presenter: MyViewPresenter = MyViewPresenter() // CRIA UMA INSTANCIA DO VIEW BINDING
    private val binding by viewBinding(FragmentMyViewBinding::bind) // USANDO O VIEW BINDING

    @Suppress("UNCHECKED_CAST")
    override fun getPresenter() = presenter as PresenterContract<ViewContract>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            computeMaxButton.setOnClickListener {
                val first = avalueEntry.text.toString()
                val second = bvalueEntry.text.toString()
                maxValueResult.text = presenter.computeMaxValue(first, second)
            }
        }
    }

    // CHAMADO PELO PRESENTER ANTES DE EFETUAR O CALCULO
    override fun showLottieAnim(animId: Int) {
        toast("Mostrar Lottie Anim With Id: $animId")
    }

    // CHAMADO PELO PRESENTER ANTES DE EFETUAR O CALCULO
    override fun hideKeyboardOnButtonClick() = hideKeyboard()


    // DE ACORDO COM O TIPO DE DESTINATION, VC NAVEGA PARA A VIEW DO SEU CASO DE USO
    override fun navigateTo(screen: ScreenDestination, animated: Boolean) {
        when (screen) {
            ScreenDestination.Home -> goBack()
            else -> Unit
        }
    }

    override fun showAlert(type: AlertType) {
        // AQUI VC APENAS PEGARIA O CONTEUDO DE ALERT TYPE E EXIBIRIA O SEU DIALOGO
        toast(getString(type.content.title!!))
    }
}