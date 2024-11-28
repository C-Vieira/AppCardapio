package com.example.appcardapio.order.presentation

import com.google.android.material.snackbar.Snackbar

class OrderActionManager(
    private val activity: OrderActivity
) {
    fun executeAction(action: OrderAction){
        when(action){
            OrderAction.UPDATE_UI -> updateUI()
            OrderAction.SHOW_ORDER_CONFIRMED_MSG -> showMessage("Pedido Confirmado com Sucesso!")
            OrderAction.SHOW_ORDER_ITEM_DELETED_MSG -> showMessage("Deletando item do pedido")
            OrderAction.SHOW_ERROR_MSG -> showMessage("Algo deu errado, Tente Novamente...")
            OrderAction.SHOW_EMPTY_ORDER_MSG -> showMessage("Pedido vazio. Adicione mais items pelo cardápio")
        }
    }

    private fun updateUI(){
        activity.updateUI()
    }

    private fun showMessage(msg: String){
        Snackbar.make(activity.findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show()
    }
}