package com.example.studiawdaniiapp.data.repository

import com.example.studiawdaniiapp.data.firebase.FirebaseSource
import com.example.studiawdaniiapp.domain.models.EmailPassword
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.domain.repository.IUserSignInRepo
import kotlinx.coroutines.tasks.await

class UserSignInRepo(private val firebase: FirebaseSource) : IUserSignInRepo {

    override suspend fun signIn(emailPassword: EmailPassword): Resource<Boolean> {
        return if (firebase.getFirebaseAuth().currentUser == null) {
            val result = firebase.getFirebaseAuth()
                .signInWithEmailAndPassword(emailPassword.email, emailPassword.password).await()

            val resultBoolean: Boolean = result.user?.email == emailPassword.email

            Resource.Success(resultBoolean)
        } else
            Resource.Failure("User is already in the system")
    }

    override suspend fun isAdmin(): Boolean {
        val firebaseUser = firebase.getFirebaseAuth().currentUser
        val db = firebase.getFirebaseFirestoreCollection().collection("users").document(firebaseUser!!.uid).get()
            .await()

        val role = db.getString("role")!!
        if (role != "admin") {
            return false
        }
        return true
    }
}