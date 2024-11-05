package com.example.appcardapio

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.appcardapio.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Glide.with(this)
            .load("https://images.pexels.com/photos/3631/summer-dessert-sweet-ice-cream.jpg?cs=srgb&dl=pexels-jeshoots-3631.jpg&fm=jpg")
            .centerCrop()
            .placeholder(ColorDrawable(Color.BLACK))
            .error(ColorDrawable(Color.LTGRAY))
            .into(binding.imageView)

        binding.forgotPasswordButton.setOnClickListener{
            // Invoke RecoverPasswordActivity
            Intent(applicationContext, RecoverPasswordActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.accountRegisterButton.setOnClickListener{
            // Invoke UserRegisterActivity
            Intent(applicationContext, UserRegisterActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}