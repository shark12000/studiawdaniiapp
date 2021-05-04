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
    single <IUserRepo> { UserRepo(firebase = get()) }
    single <IUserSignIn> { UserSignInRepo(firebase = get()) }
    single <IProfileRepo> { ProfileRepo(firebase = get()) }
    single <IUserRegistrationRepo> { UserRegistrationRepo(firebase = get()) }
    single <IEducationalInstitutionsRepo> { EducationalInstitutionsRepo() }
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





