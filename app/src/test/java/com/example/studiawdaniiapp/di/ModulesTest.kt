package com.example.studiawdaniiapp.di

import androidx.lifecycle.SavedStateHandle
import com.example.studiawdaniiapp.domain.IAdmin
import com.example.studiawdaniiapp.domain.IEducationalInstitutions
import com.example.studiawdaniiapp.domain.IProgramme
import com.example.studiawdaniiapp.domain.IStatus
import com.example.studiawdaniiapp.domain.IUser
import com.example.studiawdaniiapp.domain.repository.IAdminRepo
import com.example.studiawdaniiapp.domain.repository.IEducationalInstitutionsRepo
import com.example.studiawdaniiapp.domain.repository.IProgrammeRepo
import com.example.studiawdaniiapp.domain.repository.IStatusRepo
import com.example.studiawdaniiapp.domain.repository.IUserRegistrationRepo
import com.example.studiawdaniiapp.domain.repository.IUserRepo
import com.example.studiawdaniiapp.domain.repository.IUserSignInRepo
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.verify.verify

/** Fails when di/Modules.kt cannot build something the app asks Koin for. */
class ModulesTest {

    /** Stands in for firebaseSdkModule: getInstance() needs a real FirebaseApp. */
    private val fakeFirebaseSdkModule = module {
        single<FirebaseAuth> { mockk() }
        single<FirebaseFirestore> { mockk() }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `every repository, use case and ViewModel can be created`() {
        val koin = startKoin { modules(appModules - firebaseSdkModule + fakeFirebaseSdkModule) }.koin

        // Repositories and use cases are bound to interfaces, so only resolving them proves the wiring
        koin.get<IUserRepo>()
        koin.get<IUserSignInRepo>()
        koin.get<IUserRegistrationRepo>()
        koin.get<IEducationalInstitutionsRepo>()
        koin.get<IProgrammeRepo>()
        koin.get<IAdminRepo>()
        koin.get<IStatusRepo>()

        koin.get<IEducationalInstitutions>()
        koin.get<IUser>()
        koin.get<IProgramme>()
        koin.get<IAdmin>()
        koin.get<IStatus>()

        koin.get<MainViewModel>()
        koin.get<SignInViewModel>()
        koin.get<RegistrationViewModel>()
        koin.get<RegistrationDataViewModel>()
        koin.get<LobbyViewModel>()
        koin.get<EduViewModel>()
        koin.get<ProfileViewModel>()
        koin.get<EducationInfoViewModel>()
        koin.get<AdminViewModel>()
        // by viewModel() passes the SavedStateHandle as a parameter
        koin.get<UniversityInfoViewModel> { parametersOf(SavedStateHandle()) }
    }

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun `ViewModel constructors only ask for types the modules provide`() {
        module { includes(appModules - firebaseSdkModule) }.verify(
            extraTypes = listOf(
                // Provided by firebaseSdkModule through getInstance()
                FirebaseAuth::class,
                FirebaseFirestore::class,
                // Supplied by Koin's viewModel {} scope, not by a module
                SavedStateHandle::class,
                // ViewModel parameter with a default value; Koin never asks for it
                CoroutineDispatcher::class
            )
        )
    }

    @Test
    fun `the application starts every module`() {
        assertTrue(firebaseSdkModule in appModules)
        assertTrue(firebaseModule in appModules)
        assertTrue(repositoryModule in appModules)
        assertTrue(useCaseModule in appModules)
        assertTrue(viewModelModule in appModules)
    }
}
