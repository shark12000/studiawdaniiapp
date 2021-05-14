package com.example.studiawdaniiapp.ui.fragments.authorization.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.studiawdaniiapp.domain.models.EmailPassword
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.domain.repository.IUserRegistrationRepo
import com.google.firebase.auth.AuthResult
import java.lang.Exception

class RegistrationViewModel(private val repository: IUserRegistrationRepo) : ViewModel() {

    fun registration(emailPassword: EmailPassword) = liveData<Resource<AuthResult>> {
        emit(Resource.Loading())
        try {
            val result = repository.registration(emailPassword)
            emit(result)
        } catch (e: Exception) {
            emit(Resource.Failure(e.message.toString()))
        }
    }

    fun sendEmailVerification() = liveData<Resource<Void?>> {
        try {
            val result = repository.sendEmailVerification()
            emit(result)
        } catch (e: Exception) {
            emit(Resource.Failure(e.message.toString()))
        }
    }
}