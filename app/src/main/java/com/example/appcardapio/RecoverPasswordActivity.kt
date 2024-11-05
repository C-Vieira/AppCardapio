package com.example.appcardapio

import android.app.Activity
import android.os.Bundle
import com.example.appcardapio.databinding.RecoverPasswordViewBinding

class RecoverPasswordActivity: Activity() {
    private lateinit var binding: RecoverPasswordViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = RecoverPasswordViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goBackButton.setOnClickListener {
            finish()
        }
    }
}