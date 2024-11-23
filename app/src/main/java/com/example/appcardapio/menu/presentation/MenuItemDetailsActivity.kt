package com.example.appcardapio.menu.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.appcardapio.databinding.MenuItemDetailsViewBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuItemDetailsActivity: AppCompatActivity() {
    private lateinit var binding: MenuItemDetailsViewBinding
    private val viewModel: MenuViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MenuItemDetailsViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionManager = MenuActionManager(this)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.currentItemState.collect{ selectedItem ->
                    viewModel.onDetailsViewCreated()
                    with(binding){
                        menuItemDetailsName.text = selectedItem.name
                        menuItemDetailsDescription.text = selectedItem.description
                        menuItemDetailsPrice.text = selectedItem.price
                    }
                }
            }
        }

        binding.logoutButton.setOnClickListener {
            finish()
        }

        binding.addMenuItemButton.setOnClickListener {
            Snackbar.make(findViewById(android.R.id.content), "Item Adicionado ao Pedido", Snackbar.LENGTH_LONG).show()
        }
    }
}