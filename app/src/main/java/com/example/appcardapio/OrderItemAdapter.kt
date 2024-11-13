package com.example.appcardapio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appcardapio.databinding.OrderItemPreviewBinding

class OrderItemAdapter (
    private val items: List<OrderItem>,
    private val onClick: (OrderItem) -> Unit
) : RecyclerView.Adapter<OrderItemAdapter.ViewHolder>() {

    inner class ViewHolder(
        var binding: OrderItemPreviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentItem: OrderItem? = null

        init {
            itemView.setOnClickListener {
                currentItem?.let {
                    onClick(it)
                }
            }
        }

        fun bind(item: OrderItem) {
            currentItem = item

            binding.orderItemName.text = item.name
            binding.orderItemAmount.text = item.amount.toString()

            binding.orderItemPrice.text = item.price
            binding.orderItemPriceTotal.text = item.totalPrice.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = OrderItemPreviewBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size
}