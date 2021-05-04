package com.example.studiawdaniiapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.studiawdaniiapp.data.models.EmailPassword
import com.example.studiawdaniiapp.data.models.Resource
import com.example.studiawdaniiapp.domain.repository.IUserSignIn
import com.google.firebase.auth.AuthResult

class SignInViewModel(private val repository: IUserSignIn) : ViewModel() {

    fun signIn(emailPassword: EmailPassword) = liveData<Resource<AuthResult>> {
        try {
            val result = repository.signIn(emailPassword)
            emit(result)
        } catch (e: Exception) {
            emit(Resource.Failure(e.message.toString()))
        }
    }
}