package com.example.appcardapio.login.presentation

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.appcardapio.R
import com.example.appcardapio.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionManager = LoginActionManager(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Glide.with(this)
            .load(viewModel.getLogoImage() ?: ColorDrawable(Color.BLACK))
            .centerCrop()
            .placeholder(ColorDrawable(Color.BLACK))
            .error(ColorDrawable(Color.LTGRAY))
            .into(binding.imageView)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiAction.collect { action ->
                    actionManager.executeAction(action)
                }
            }
        }

        viewModel.onViewCreated()

        with(binding){
            loginButton.setOnClickListener {
                viewModel.onLoginClicked(getEmailText(), getPasswordText())
                clearTextFields()
            }

            forgotPasswordButton.setOnClickListener{
                // Invoke RecoverPasswordActivity
                Intent(applicationContext, RecoverPasswordActivity::class.java).also {
                    startActivity(it)
                }
            }

            accountRegisterButton.setOnClickListener{
                // Invoke UserRegisterActivity
                Intent(applicationContext, UserRegisterActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }

    private fun clearTextFields() {
        binding.emailTxtField.text.clear()
        binding.passwordTxtField.text.clear()
    }

    private fun getEmailText() = binding.emailTxtField.text.toString()

    private fun getPasswordText() = binding.passwordTxtField.text.toString()
}