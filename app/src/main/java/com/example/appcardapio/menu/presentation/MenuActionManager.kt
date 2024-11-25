package com.example.appcardapio.menu.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.appcardapio.order.presentation.OrderActivity
import com.google.android.material.snackbar.Snackbar

class MenuActionManager(
    private val activity: AppCompatActivity
) {
    fun executeAction(action: MenuAction){
        when(action){
            MenuAction.NAVIGATE_TO_DETAILS -> navigateToDetails()
            MenuAction.NAVIGATE_TO_ORDER -> navigateToOrder()
            MenuAction.SHOW_ADDED_TO_ORDER_MSG -> showMessage("Item Adicionado ao Pedido")
            MenuAction.SHOW_ERROR_MSG -> showMessage("Algo deu errado, Tente Novamente...")
        }
    }

    private fun navigateToDetails(){
        // Invoke MeniItemDetailsActivity
        Intent(activity.applicationContext, MenuItemDetailsActivity::class.java).also {
            activity.startActivity(it)
        }
    }

    private fun navigateToOrder(){
        // Invoke OrderActivity
        Intent(activity.applicationContext, OrderActivity::class.java).also {
            activity.startActivity(it)
        }
    }

    private fun showMessage(msg: String){
        Snackbar.make(activity.findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show()
    }
}