package com.example.studiawdaniiapp.di

import com.example.studiawdaniiapp.data.firebase.FirebaseSource
import com.example.studiawdaniiapp.data.repository.UserRepositoryImpl
import com.example.studiawdaniiapp.ui.recyclerView.EduListAdapter
import com.example.studiawdaniiapp.viewmodel.AuthViewModel
import com.example.studiawdaniiapp.viewmodel.EduViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val firebaseModule = module {
    single { FirebaseSource() }
}

val repositoryModule = module {
    fun provideUserRepository(firebase: FirebaseSource): UserRepositoryImpl {
        return UserRepositoryImpl(firebase)
    }
    single { provideUserRepository(get()) }
}

val viewModelModule = module {

    viewModel { AuthViewModel() }

    viewModel { EduViewModel() }
}

val adapterModule = module {

    //single { EduListAdapter() }
}





