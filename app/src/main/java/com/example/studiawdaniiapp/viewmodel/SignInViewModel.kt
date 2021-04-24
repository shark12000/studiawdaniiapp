package com.example.studiawdaniiapp.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.studiawdaniiapp.data.repository.UserRepository
import com.google.firebase.auth.FirebaseUser
import org.koin.core.KoinComponent
import org.koin.core.inject

class SignInViewModel: ViewModel(), KoinComponent
{

    private val userRepository: UserRepository by inject()

    fun getCurrentUser() : LiveData<FirebaseUser> {
        return userRepository.getCurrentUser()
    }

}