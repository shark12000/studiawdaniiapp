package com.example.studiawdaniiapp.di

import com.example.studiawdaniiapp.data.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    fun provideUserRepository(): UserRepository {
        return UserRepository()
    }
    single { provideUserRepository() }
}



