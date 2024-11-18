package com.example.appcardapio.login.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.appcardapio.MenuActivity
import com.example.appcardapio.databinding.UserRegisterViewBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserRegisterActivity: AppCompatActivity() {
    private lateinit var binding: UserRegisterViewBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = UserRegisterViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiAction.collect { action ->
                    executeAction(action)
                }
            }
        }

        viewModel.onViewCreated()

        binding.registerButton.setOnClickListener {
            viewModel.onCreateAccountClicked(getEmailText(), getPasswordText())
        }

        binding.goBackButton.setOnClickListener {
            finish()
        }
    }

    private fun executeAction(action: LoginAction){
        when(action){
            LoginAction.NAVIGATE_HOME -> navigateHome()
            LoginAction.SHOW_ERROR_MSG -> showMessage("Ocorreu um erro...")
            LoginAction.SHOW_RECOVER_MSG -> showMessage("Email de verificação enviado")
        }
    }

    private fun navigateHome(){
        // Invoke MenuActivity
        Intent(applicationContext, MenuActivity::class.java).also {
            startActivity(it)
        }
        finish()
    }

    private fun showMessage(msg: String){
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
    }

    private fun getEmailText() = binding.emailTxtField.text.toString()

    private fun getPasswordText() = binding.passwordTxtField.text.toString()
}