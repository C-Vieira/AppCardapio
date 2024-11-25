package com.example.appcardapio

import android.app.Application
import com.example.appcardapio.login.di.loginModule
import com.example.appcardapio.menu.di.menuModule
import com.example.appcardapio.order.di.orderModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@MainApplication)
            modules(loginModule, menuModule, orderModule)
        }
    }
}