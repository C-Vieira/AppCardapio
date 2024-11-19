package com.example.appcardapio.login.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.appcardapio.databinding.UserRegisterViewBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserRegisterActivity: AppCompatActivity() {
    private lateinit var binding: UserRegisterViewBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = UserRegisterViewBinding.inflate(layoutInflater)
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

        binding.registerButton.setOnClickListener {
            viewModel.onCreateAccountClicked(getUserNameText() ,getEmailText(), getPasswordText(), getConfirmPasswordText())
            clearTextFields()
        }

        binding.goBackButton.setOnClickListener {
            finish()
        }
    }

    private fun clearTextFields() {
        binding.nameTxtField.text.clear()
        binding.emailTxtField.text.clear()
        binding.passwordTxtField.text.clear()
        binding.confirmPasswordTxtField.text.clear()
    }

    private fun getUserNameText() = binding.nameTxtField.text.toString()

    private fun getEmailText() = binding.emailTxtField.text.toString()

    private fun getPasswordText() = binding.passwordTxtField.text.toString()

    private fun getConfirmPasswordText() = binding.confirmPasswordTxtField.text.toString()
}