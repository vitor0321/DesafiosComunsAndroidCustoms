package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.databinding.FragmentMainBinding
import com.example.desafioscomunsandroidcustoms.presentation.ui.activity.bloquear_captura_tela.BlockScreenShotsActivity
import com.example.desafioscomunsandroidcustoms.util.viewBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val component = mapOf(
            "Solicite Permissões" to R.id.action_mainFragment_to_requestPermissionFragment,
            "Start for Result" to R.id.action_mainFragment_to_requestResultFragment,
            "Mostra e Esconder Teclado" to R.id.action_mainFragment_to_tecladoFragment,
            "Vibrar Button" to R.id.action_mainFragment_to_vibrarFragment,
            "Check se tem Internet" to R.id.action_mainFragment_to_hasInternetFragment,
            "Web View Dark" to R.id.action_mainFragment_to_darkWebViewFragment,
            "Check EditText" to R.id.action_mainFragment_to_checkEditTextFragment,
            "Check se é emulador" to R.id.action_mainFragment_to_checkIfEmulatorFragment,
            "Button Back" to R.id.action_mainFragment_to_backPressedFragment,
            "Biometria" to R.id.action_mainFragment_to_biometryFragment,
            "Full Screen Dialog" to R.id.action_mainFragment_to_fullscreenDialogFragment,
            "Alert Dialog" to R.id.action_mainFragment_to_customDialogFragment,
            "Polling Api" to R.id.action_mainFragment_to_pollingApiFragment,
            "Button Sheet" to R.id.action_mainFragment_to_bottomSheetDialogFragment,
            "MVP Button" to R.id.action_mainFragment_to_myViewFragment,
            "RecyclerView Custumizado" to R.id.action_mainFragment_to_itemDecoratorFragment,
            "Mostrar Teclado Edit" to R.id.action_mainFragment_to_showEditWithTecladoFragment,
            "Filtrar Lista" to R.id.action_mainFragment_to_searchViewFragment,
            "Plural de String" to R.id.action_mainFragment_to_pluralFragment,
            "Linha do tempo" to R.id.action_mainFragment_to_linhaDoTempoFragment,
            "ScrollView Dinamico" to R.id.action_mainFragment_to_percentualScrollViewFragment,
            "Text Highlighter" to R.id.action_mainFragment_to_textHighlighterFragment,
            "Restoreable Action Dialog" to R.id.action_mainFragment_to_restoreableActionDialog
        )

        binding.listItemsRecycler.apply {
            adapter =
                MainFragmentAdapter(component.keys.toList()) { _: Int, name: String, _: View ->
                    component[name]?.let { findNavController().navigate(it) }
                }
            addItemDecoration(DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL))
        }

        binding.blockScreenShotsButton.setOnClickListener {
            val intent = Intent(activity, BlockScreenShotsActivity::class.java)
            activity?.startActivity(intent)
        }
    }

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