package com.example.studiawdaniiapp.data.repository

import com.example.studiawdaniiapp.data.firebase.FirebaseSource
import com.example.studiawdaniiapp.domain.models.EmailPassword
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.domain.repository.IUserRegistrationRepo
import kotlinx.coroutines.tasks.await

class UserRegistrationRepo(private val firebase: FirebaseSource) : IUserRegistrationRepo {

    override suspend fun registration(emailPassword: EmailPassword): Resource<Boolean> {
        return try {
            val result = firebase.getFirebaseAuth()
                .createUserWithEmailAndPassword(emailPassword.email, emailPassword.password).await();

            val resultBoolean: Boolean = result.user?.email == emailPassword.email

             Resource.Success(resultBoolean)
        } catch (e: Exception) {
             Resource.Failure(e.toString())
        }
    }
}