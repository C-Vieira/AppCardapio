package com.example.appcardapio.menu.presentation

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.appcardapio.databinding.MenuItemDetailsViewBinding
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
                        Glide.with(this@MenuItemDetailsActivity)
                            .load(viewModel.getMenuItemImage(selectedItem.name) ?: ColorDrawable(Color.BLACK))
                            .centerCrop()
                            .placeholder(ColorDrawable(Color.BLACK))
                            .error(ColorDrawable(Color.LTGRAY))
                            .into(menuItemDetailsImageView)

                        menuItemDetailsName.text = selectedItem.name
                        menuItemDetailsDescription.text = selectedItem.description
                        menuItemDetailsPrice.text = selectedItem.price
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiAction.collect{ action ->
                    actionManager.executeAction(action)
                }
            }
        }

        binding.logoutButton.setOnClickListener {
            finish()
        }

        binding.addMenuItemButton.setOnClickListener {
            viewModel.onAddToOrderClicked(getMenuItemName(), getMenuItemPrice(), 1)
        }
    }

    private fun getMenuItemName(): String = binding.menuItemDetailsName.text.toString()

    private fun getMenuItemPrice(): String = binding.menuItemDetailsPrice.text.toString()
}