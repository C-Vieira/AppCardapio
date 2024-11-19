package com.example.appcardapio.login.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.appcardapio.databinding.RecoverPasswordViewBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecoverPasswordActivity: AppCompatActivity() {
    private lateinit var binding: RecoverPasswordViewBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = RecoverPasswordViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionManager = LoginActionManager(this)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiAction.collect { action ->
                    actionManager.executeAction(action)
                }
            }
        }

        viewModel.onViewCreated()

        binding.clearPasswordButton.setOnClickListener {
            viewModel.onRecoverPasswordClicked(getEmailText())
            clearTextFields()
        }

        binding.goBackButton.setOnClickListener {
            finish()
        }
    }

    private fun clearTextFields() = binding.emailTxtField.text.clear()

    private fun getEmailText() = binding.emailTxtField.text.toString()
}