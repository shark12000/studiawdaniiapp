package com.example.studiawdaniiapp.data.repository

import com.example.studiawdaniiapp.data.firebase.FirebaseSource
import com.example.studiawdaniiapp.domain.models.EmailPassword
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.domain.repository.IUserRegistrationRepo
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.tasks.await

class UserRegistrationRepo(private val firebase: FirebaseSource) : IUserRegistrationRepo {

    override suspend fun registration(emailPassword: EmailPassword): Resource<AuthResult> {
        val authResult = firebase.getFirebaseAuth()
            .createUserWithEmailAndPassword(emailPassword.email, emailPassword.password).await();
        return Resource.Success(authResult)
    }

    override suspend fun sendEmailVerification(): Resource<Void?> {
        val result = firebase.getFirebaseAuth().currentUser?.sendEmailVerification()?.await()
        return Resource.Success(result)
    }

}