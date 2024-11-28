package com.example.appcardapio.order.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appcardapio.databinding.OrderItemPreviewBinding
import com.example.appcardapio.order.model.OrderItem

class OrderItemAdapter (
    private val items: List<OrderItem>,
    private val onIncrementOrDecrementButtonClick: (OrderItem, Int) -> Unit,
    private val onDeleteButtonClick: (OrderItem) -> Unit
) : RecyclerView.Adapter<OrderItemAdapter.ViewHolder>() {

    inner class ViewHolder(
        var binding: OrderItemPreviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentItem: OrderItem? = null

        init {
            binding.orderItemIncButton.setOnClickListener{
                currentItem?.let {
                    onIncrementOrDecrementButtonClick(
                        OrderItem(it.name, it.price, it.amount), it.amount + 1)
                }
            }

            binding.orderItemDecButton.setOnClickListener{
                currentItem?.let {
                    onIncrementOrDecrementButtonClick(
                        OrderItem(it.name, it.price, it.amount), it.amount - 1)
                }
            }

            binding.deleteOrderItemImageButton.setOnClickListener{
                currentItem?.let {
                    onDeleteButtonClick(OrderItem(currentItem!!.name, currentItem!!.price, currentItem!!.amount))
                }
            }
        }

        fun bind(item: OrderItem) {
            currentItem = item

            binding.orderItemName.text = item.name
            binding.orderItemAmount.text = item.amount.toString()

            binding.orderItemAmountInput.setText(item.amount.toString())

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