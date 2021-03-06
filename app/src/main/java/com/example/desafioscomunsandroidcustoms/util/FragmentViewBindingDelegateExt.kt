package com.example.desafioscomunsandroidcustoms.util

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

// 1- Definir dependencia no build.gradle
// 2 - Criar classe inicializa o view binding
// 3 - Definir extensão de kotlin para usar em seu projeto/classes

class FragmentViewBindingDelegate<VB : ViewBinding>(
    val fragment: Fragment,
    val viewBindingFactory: (View) -> VB
) : ReadOnlyProperty<Fragment, VB> {

    private var binding: VB? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {

            //remove o binding ao destruir o fragment
            val viewLifecycleOwnerLiveDataObserver = Observer<LifecycleOwner?> {
                it?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
                    override fun onDestroy(owner: LifecycleOwner) {
                        super.onDestroy(owner)
                        binding = null
                    }
                })
            }

            //observa o ciclo de vida ao cirar um fragment
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observeForever {
                    viewLifecycleOwnerLiveDataObserver
                }
            }

            //remove observer de lifecycle quando fragment é destruido
            override fun onDestroy(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.removeObserver {
                    viewLifecycleOwnerLiveDataObserver
                }
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {

        //se existe retorna direto
        binding?.let { return it }

        //so cria se estiver ao menos inicializado
        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throw  IllegalStateException("Should not attempt to get bindings when Fragment views are destroyed")
        }

        //do contrario cria o binding
        return viewBindingFactory(thisRef.requireView()).also { this.binding = it }
    }
}

//Passe "NomeDoXMLBinding::bind" por referencia a este metodo
fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) =
    FragmentViewBindingDelegate(this, viewBindingFactory)