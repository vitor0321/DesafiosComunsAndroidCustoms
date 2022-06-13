package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.show_edit_with_teclado

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.viewModels
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.databinding.FragmentShowEditWithTecladoBinding
import com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.BaseFragment
import com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.view_model.ExposeObserveViewModel
import com.example.desafioscomunsandroidcustoms.util.hideKeyboard
import com.example.desafioscomunsandroidcustoms.util.setOnEnterKeyListener
import com.example.desafioscomunsandroidcustoms.util.toast
import java.util.Locale.FRENCH
import java.util.Locale.GERMAN

class ShowEditWithTecladoFragment : BaseFragment<FragmentShowEditWithTecladoBinding>() {

    override fun getViewBinding(): FragmentShowEditWithTecladoBinding =
        FragmentShowEditWithTecladoBinding.inflate(layoutInflater)

    val loginViewModel: ExposeObserveViewModel by viewModels()

    // 1) como interceptar ação de ENTER no teclado virtual
    // 2) Identificar qual tecla foi pressionada, definir extensão
    // 3) Reagir ao toque realizando a ação do seu caso de uso
    override fun initializeUi() {
        with(binding) {
            password.setOnEnterKeyListener(getOnLoginClickedAction())
        }

        // 0) como disparar o evento que altera o modelo e se reflete na Interface
        binding.impressum.setOnClickListener {
            loginViewModel.changeAppName("Novo AppName")
        }

        loginViewModel.observableName.observe(viewLifecycleOwner) { newAppName ->
            binding.title.text = newAppName
        }

        // chamar quando oportuno
        startProgressBar()
        hideProgressBar()
    }

    private fun getOnLoginClickedAction(): () -> Unit {
        val onClickAction = {
            hideKeyboard()
            login()
        }
        return onClickAction
    }

    private fun login() = toast("logged In")


    override fun showActionBarOptionMenu() = true

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.langrage_settings, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_fr -> {
                //setLanguage(FRENCH)
                return true
            }
            R.id.action_de -> {
                //setLanguage(GERMAN)
                return true
            }
            else -> Unit
        }
        return super.onOptionsItemSelected(item)
    }
}