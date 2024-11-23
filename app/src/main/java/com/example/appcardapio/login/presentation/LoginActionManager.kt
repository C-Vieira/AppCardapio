package com.example.appcardapio.login.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.appcardapio.menu.presentation.MenuActivity
import com.google.android.material.snackbar.Snackbar

class LoginActionManager(
    private val activity: AppCompatActivity
) {
    fun executeAction(action: LoginAction){
        when(action){
            LoginAction.NAVIGATE_HOME -> navigateHome()
            LoginAction.SHOW_ACCOUNT_CREATED_MSG -> showMessage("Conta criada com sucesso!")
            LoginAction.SHOW_RECOVER_MSG -> showMessage("Email de verificação enviado")
            LoginAction.SHOW_ERROR_MSG -> showMessage("Ocorreu um erro, tente novamente...")
            LoginAction.SHOW_ERROR_MSG_EMPTY -> showMessage("Por favor preencha todos os campos")
            LoginAction.SHOW_ERROR_MSG_BAD_EMAIL_FORMAT -> showMessage("Formato de email incorreto")
            LoginAction.SHOW_ERROR_MSG_WRONG_CREDENTIALS -> showMessage("Email ou senha incorretos")
            LoginAction.SHOW_ERROR_MSG_INVALID_PASSWORD -> showMessage("Senha inválida")
            LoginAction.SHOW_ERROR_MSG_PASSWORD_MISMATCH -> showMessage("Senha e confirmação de senha diferentes")
        }
    }

    private fun navigateHome(){
        // Invoke MenuActivity
        Intent(activity.applicationContext, MenuActivity::class.java).also {
            activity.startActivity(it)
        }
    }

    private fun showMessage(msg: String){
        Snackbar.make(activity.findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show()
    }
}