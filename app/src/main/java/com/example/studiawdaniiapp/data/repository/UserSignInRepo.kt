package com.example.studiawdaniiapp.data.repository

import com.example.studiawdaniiapp.data.firebase.FirebaseSource
import com.example.studiawdaniiapp.data.models.EmailPassword
import com.example.studiawdaniiapp.data.models.Resource
import com.example.studiawdaniiapp.domain.repository.IUserSignIn
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.tasks.await

class UserSignInRepo(private val firebase: FirebaseSource) : IUserSignIn {


    override suspend fun signIn(emailPassword: EmailPassword): Resource<AuthResult> {
        val result = firebase.getFirebaseAuth()
            .signInWithEmailAndPassword(emailPassword.email, emailPassword.password).await()

        return Resource.Success(result)
    }

}