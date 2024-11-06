package com.example.appcardapio

import android.app.Activity
import android.os.Bundle
import com.example.appcardapio.databinding.MenuItemDetailsViewBinding
import com.google.android.material.snackbar.Snackbar

class MenuItemDetailsActivity: Activity() {
    private lateinit var binding: MenuItemDetailsViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MenuItemDetailsViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logoutButton.setOnClickListener {
            finish()
        }

        binding.addMenuItemButton.setOnClickListener {
            Snackbar.make(findViewById(android.R.id.content), "Item Adicionado ao Pedido", Snackbar.LENGTH_LONG).show()
        }
    }
}