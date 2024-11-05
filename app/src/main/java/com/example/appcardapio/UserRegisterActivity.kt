package com.example.appcardapio

import android.app.Activity
import android.os.Bundle
import com.example.appcardapio.databinding.UserRegisterViewBinding

class UserRegisterActivity: Activity() {
    private lateinit var binding: UserRegisterViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = UserRegisterViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goBackButton.setOnClickListener {
            finish()
        }
    }
}