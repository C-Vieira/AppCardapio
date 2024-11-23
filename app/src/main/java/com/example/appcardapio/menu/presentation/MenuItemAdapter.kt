package com.example.appcardapio.menu.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appcardapio.databinding.MenuItemPreviewBinding
import com.example.appcardapio.menu.model.MenuItem

class MenuItemAdapter (
    private val items: List<MenuItem>,
    private val onClick: (MenuItem) -> Unit
) : RecyclerView.Adapter<MenuItemAdapter.ViewHolder>() {

    inner class ViewHolder(
        var binding: MenuItemPreviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentItem: MenuItem? = null

        init {
            itemView.setOnClickListener {
                currentItem?.let {
                    onClick(it)
                }
            }
        }

        fun bind(item: MenuItem) {
            currentItem = item

            binding.menuItemName.text = item.name
            binding.menuItemPrice.text = item.price
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MenuItemPreviewBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size
}