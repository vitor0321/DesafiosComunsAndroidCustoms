package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafioscomunsandroidcustoms.databinding.ItemMainFragmentBinding

class MainFragmentAdapter(
    private val items: List<String>,
    val clickListener: (position: Int, value: String, view: View) -> Unit
) : RecyclerView.Adapter<MainFragmentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMainFragmentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onItemBind(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: ItemMainFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onItemBind(text: String, position: Int) {
            binding.apply {
                title.text = text
                title.setOnClickListener {
                    clickListener(position, text, it)
                }
            }
        }
    }
}