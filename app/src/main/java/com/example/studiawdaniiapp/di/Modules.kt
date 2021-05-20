package com.example.studiawdaniiapp.di

import com.example.studiawdaniiapp.data.firebase.FirebaseSource
import com.example.studiawdaniiapp.data.repository.*
import com.example.studiawdaniiapp.domain.*
import com.example.studiawdaniiapp.domain.repository.*
import com.example.studiawdaniiapp.ui.MainViewModel
import com.example.studiawdaniiapp.ui.fragments.admin.AdminViewModel
import com.example.studiawdaniiapp.ui.fragments.authorization.login.SignInViewModel
import com.example.studiawdaniiapp.ui.fragments.authorization.registration.RegistrationViewModel
import com.example.studiawdaniiapp.ui.fragments.authorization.registrationData.RegistrationDataViewModel
import com.example.studiawdaniiapp.ui.fragments.home.educationInfo.EducationInfoViewModel
import com.example.studiawdaniiapp.ui.fragments.home.lobby.LobbyViewModel
import com.example.studiawdaniiapp.ui.fragments.home.profile.ProfileViewModel
import com.example.studiawdaniiapp.ui.fragments.home.universityInfo.UniversityInfoViewModel
import com.example.studiawdaniiapp.ui.fragments.home.universityList.EduViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val firebaseModule = module {
    single { FirebaseSource() }
}

val repositoryModule = module {
    single <IUserRepo> { UserRepo(firebase = get()) }
    single <IUserSignInRepo> { UserSignInRepo(firebase = get()) }
    single <IUserRegistrationRepo> { UserRegistrationRepo(firebase = get()) }
    single <IEducationalInstitutionsRepo> { EducationalInstitutionsRepo() }
    single <IProgrammeRepo> { ProgrammeRepo(firebaseSource = get()) }
    single <IAdminRepo> { AdminRepo(firebaseSource = get())}
    single <IStatusRepo> { StatusRepo(firebaseSource = get()) }
}

val useCaseModule = module {
    factory<IEducationalInstitutions> { EducationalInstitutionsImpl(repository = get()) }
    factory<IUser> { UserImpl(repository = get()) }
    factory<IProgramme> { ProgrammeImpl(repository = get()) }
    factory<IAdmin> { AdminImpl(repository = get()) }
    factory<IStatus> { StatusImpl(repository = get()) }
}

val viewModelModule = module {
    viewModel { EduViewModel(useCase = get()) }
    viewModel { SignInViewModel(repository = get()) }
    viewModel { RegistrationViewModel(repository = get()) }
    viewModel { LobbyViewModel(repository = get(), useCase = get()) }
    viewModel { RegistrationDataViewModel(useCase = get()) }
    viewModel { ProfileViewModel(repository = get()) }
    viewModel { MainViewModel(repository = get()) }
    viewModel { UniversityInfoViewModel(get(), get()) }
    viewModel { EducationInfoViewModel(useCase = get(), useCaseStatus = get()) }
    viewModel { AdminViewModel(useCase = get(), useCaseUser = get()) }
}





