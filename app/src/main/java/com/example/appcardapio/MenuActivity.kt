package com.example.appcardapio

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcardapio.databinding.MenuViewBinding
import com.google.android.material.snackbar.Snackbar

class MenuActivity: Activity() {
    private lateinit var binding: MenuViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MenuViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val menuItemsSource = mutableListOf(
            MenuItem("Item1", "R$ 19,99"),
            MenuItem("Item2", "R$ 19,99"),
            MenuItem("Item3", "R$ 19,99"),
            MenuItem("Item4", "R$ 19,99"),
            MenuItem("Item5", "R$ 19,99"),
            MenuItem("Item6", "R$ 19,99"),
            MenuItem("Item7", "R$ 19,99"),
            MenuItem("Item8", "R$ 19,99"),
            MenuItem("Item9", "R$ 19,99"),
            MenuItem("Item10", "R$ 19,99"),
            MenuItem("Item11", "R$ 19,99"),
            MenuItem("Item12", "R$ 19,99")
        )

        val adapter = MenuItemAdapter(menuItemsSource, ::onListItemClicked)
        val layoutManager = LinearLayoutManager(this)

        binding.menuItemsRecylerview.adapter = adapter
        binding.menuItemsRecylerview.layoutManager = layoutManager

        binding.reviewOrderButton.setOnClickListener {
            // Invoke OrderActivity
            Intent(applicationContext, OrderActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.logoutButton.setOnClickListener {
            finish()
        }
    }

    private fun onListItemClicked(item: MenuItem){
        // Invoke MenuItemDetailsActivity
        Intent(applicationContext, MenuItemDetailsActivity::class.java).also {
            startActivity(it)
        }
    }
}