package com.example.studiawdaniiapp.di

import com.example.studiawdaniiapp.data.firebase.FirebaseSource
import com.example.studiawdaniiapp.data.repository.*
import com.example.studiawdaniiapp.domain.EducationalInstitutionsImpl
import com.example.studiawdaniiapp.domain.IEducationalInstitutions
import com.example.studiawdaniiapp.domain.repository.*
import com.example.studiawdaniiapp.ui.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val firebaseModule = module {
    single { FirebaseSource() }
}

val repositoryModule = module {
    factory<IUserRepo> { UserRepo(firebase = get()) }
    factory<IUserSignIn> { UserSignInRepo(firebase = get()) }
    factory<IProfileRepo> { ProfileRepo(firebase = get()) }
    factory<IUserRegistrationRepo> { UserRegistrationRepo(firebase = get()) }
    factory<IEducationalInstitutionsRepo> { EducationalInstitutionsRepo() }
}

val useCaseModule = module {
    factory<IEducationalInstitutions> { EducationalInstitutionsImpl(repository = get()) }
}

val viewModelModule = module {
    viewModel { EduViewModel(useCase = get()) }
    viewModel { SignInViewModel(repository = get()) }
    viewModel { RegistrationViewModel(repository = get()) }
    viewModel { LobbyViewModel(repository = get()) }
    viewModel { RegistrationDataViewModel(repository = get()) }
    viewModel { ProfileViewModel(repository = get(), userRepository = get()) }
}





