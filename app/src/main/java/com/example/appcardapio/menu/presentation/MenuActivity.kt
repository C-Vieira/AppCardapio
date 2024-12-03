package com.example.appcardapio.menu.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcardapio.databinding.MenuViewBinding
import com.example.appcardapio.menu.model.MenuItem
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuActivity: AppCompatActivity() {
    private lateinit var binding: MenuViewBinding
    private val viewModel: MenuViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MenuViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionManager = MenuActionManager(this)

        val partyMenuItemSource = viewModel.getMenuItemSource().filter { it.category == "festa" }
        val cupsMenuItemSource = viewModel.getMenuItemSource().filter { it.category == "copos" }
        val cakeMenuItemSource = viewModel.getMenuItemSource().filter { it.category == "bolos" }

        val partyHeaderAdapter = CategoryHeaderAdapter("Doces de Festa")
        val cupsHeaderAdapter = CategoryHeaderAdapter("Copos da Felicidade")
        val cakeHeaderAdapter = CategoryHeaderAdapter("Bolos")

        val partyMenuItemAdapter = MenuItemAdapter(partyMenuItemSource, ::onListItemClicked)
        val cupsMenuItemAdapter = MenuItemAdapter(cupsMenuItemSource, ::onListItemClicked)
        val cakeMenuItemAdapter = MenuItemAdapter(cakeMenuItemSource, ::onListItemClicked)

        val adapter = ConcatAdapter(
            partyHeaderAdapter, partyMenuItemAdapter,
            cupsHeaderAdapter, cupsMenuItemAdapter,
            cakeHeaderAdapter, cakeMenuItemAdapter
        )

        val layoutManager = LinearLayoutManager(this)

        binding.menuItemsRecylerview.adapter = adapter
        binding.menuItemsRecylerview.layoutManager = layoutManager

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiAction.collect { action ->
                    actionManager.executeAction(action)
                }
            }
        }

        binding.reviewOrderButton.setOnClickListener {
            viewModel.onOrderClicked()
        }

        binding.logoutButton.setOnClickListener {
            finish()
        }
    }

    private fun onListItemClicked(item: MenuItem){
        viewModel.onMenuItemClicked(item)
    }
}