package com.example.appcardapio.login.presentation

import android.graphics.Bitmap
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcardapio.login.data.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewModel(
    private val loginRepository: LoginRepository
): ViewModel() {

    // Exception Strings
    private val emptyTxtFields = "Given String is empty or null"
    private val badEmailFormat = "The email address is badly formatted."
    private val wrongCredentials = "The supplied auth credential is incorrect, malformed or has expired."
    private val invalidPassword = "The given password is invalid. [ Password should be at least 6 characters ]"
    private val passwordMismatch = "Password and password confirmation do not match"

    private val _uiAction = MutableSharedFlow<LoginAction>()
    val uiAction: SharedFlow<LoginAction> = _uiAction.asSharedFlow()

    fun onViewCreated(){
        viewModelScope.launch {
            if(loginRepository.isSessionValid()){
                _uiAction.emit(LoginAction.NAVIGATE_HOME)
            }
        }
    }

    fun getLogoImage(): Bitmap? = runBlocking {
        loginRepository.getLogoImage()
    }

    fun onLoginClicked(emailText: String, passwordText: String){
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                loginRepository.login(emailText, passwordText)
                _uiAction.emit(LoginAction.NAVIGATE_HOME)
            }.onFailure { e ->
                when(e.message){
                    emptyTxtFields -> _uiAction.emit(LoginAction.SHOW_ERROR_MSG_EMPTY)
                    badEmailFormat -> _uiAction.emit(LoginAction.SHOW_ERROR_MSG_BAD_EMAIL_FORMAT)
                    wrongCredentials -> _uiAction.emit(LoginAction.SHOW_ERROR_MSG_WRONG_CREDENTIALS)
                    else -> _uiAction.emit(LoginAction.SHOW_ERROR_MSG)
                }

                Log.e("LOGIN", e.message ?: "unknown", e)
            }
        }
    }

    fun onCreateAccountClicked(userNameText: String, emailText: String, passwordText: String, confirmPasswordText: String){
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                if(userNameText.isEmpty()
                    || emailText.isEmpty()
                    || confirmPasswordText.isEmpty()
                    || passwordText.isEmpty()) throw Throwable(emptyTxtFields)
                if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) throw Throwable(badEmailFormat)
                if(passwordText != confirmPasswordText) throw Throwable(passwordMismatch)
                loginRepository.createAccount(userNameText, emailText, passwordText)
                _uiAction.emit(LoginAction.SHOW_ACCOUNT_CREATED_MSG)
            }.onFailure { e ->
                when(e.message){
                    emptyTxtFields -> _uiAction.emit(LoginAction.SHOW_ERROR_MSG_EMPTY)
                    badEmailFormat -> _uiAction.emit(LoginAction.SHOW_ERROR_MSG_BAD_EMAIL_FORMAT)
                    invalidPassword -> _uiAction.emit(LoginAction.SHOW_ERROR_MSG_INVALID_PASSWORD)
                    passwordMismatch -> _uiAction.emit(LoginAction.SHOW_ERROR_MSG_PASSWORD_MISMATCH)
                    else -> _uiAction.emit(LoginAction.SHOW_ERROR_MSG)
                }

                Log.e("LOGIN", e.message ?: "unknown", e)
            }
        }
    }

    fun onRecoverPasswordClicked(emailText: String){
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                loginRepository.recover(emailText)
                _uiAction.emit(LoginAction.SHOW_RECOVER_MSG)
            }.onFailure { e ->
                when(e.message){
                    emptyTxtFields -> _uiAction.emit(LoginAction.SHOW_ERROR_MSG_EMPTY)
                    badEmailFormat -> _uiAction.emit(LoginAction.SHOW_ERROR_MSG_BAD_EMAIL_FORMAT)
                    else -> _uiAction.emit(LoginAction.SHOW_ERROR_MSG)
                }

                Log.e("LOGIN", e.message ?: "unknown", e)
            }
        }
    }
}