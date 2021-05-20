package com.example.studiawdaniiapp

import android.app.Application
import com.example.studiawdaniiapp.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(
                listOf(
                    repositoryModule,
                    viewModelModule,
                    firebaseModule,
                    useCaseModule
                )
            )
        }
    }

}