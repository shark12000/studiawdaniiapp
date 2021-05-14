package com.example.studiawdaniiapp.ui.fragments.authorization.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.studiawdaniiapp.domain.models.AuthResult
import com.example.studiawdaniiapp.domain.models.EmailPassword
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.domain.repository.IUserSignInRepo

class SignInViewModel(private val repository: IUserSignInRepo) : ViewModel() {

    fun signIn(emailPassword: EmailPassword) = liveData<Resource<AuthResult>> {
        try {
            val result = repository.signIn(emailPassword)
            emit(result)
        } catch (e: Exception) {
            emit(Resource.Failure(e.message.toString()))
        }
    }
}