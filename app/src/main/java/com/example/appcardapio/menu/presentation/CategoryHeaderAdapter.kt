package com.example.appcardapio.menu.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appcardapio.databinding.CategoryHeaderPreviewBinding

class CategoryHeaderAdapter(
    private val category: String
): RecyclerView.Adapter<CategoryHeaderAdapter.HeaderViewHolder>() {

    inner class HeaderViewHolder(
        var binding: CategoryHeaderPreviewBinding
    ): RecyclerView.ViewHolder(binding.root) {
        private val categoryTextView = binding.categoryText

        fun bind(category: String){
            categoryTextView.text = category
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val binding = CategoryHeaderPreviewBinding.inflate(LayoutInflater.from(parent.context))
        return HeaderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.bind(category)
    }

    override fun getItemCount(): Int = 1
}