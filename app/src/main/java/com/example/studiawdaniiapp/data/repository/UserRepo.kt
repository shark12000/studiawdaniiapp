package com.example.studiawdaniiapp.data.repository

import com.example.studiawdaniiapp.data.firebase.FirebaseSource
import com.example.studiawdaniiapp.domain.models.ProfileData
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.domain.repository.IUserRepo
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await


class UserRepo(private val firebase: FirebaseSource) : IUserRepo {

    override suspend fun getFirebaseUser(): Resource<FirebaseUser?> {
        val user = firebase.getFirebaseAuth().currentUser
        return Resource.Success(user)
    }

    override suspend fun getUser(): Resource<ProfileData> {
        val firebaseUser = firebase.getFirebaseAuth().currentUser
        val db = firebase.getFirebaseFirestoreCollection("users").document(firebaseUser!!.uid).get()
            .await()
        lateinit var user: ProfileData

        val firstName = db.getString("firstName")
        val secondName = db.getString("secondName")
        val mobilePhone = db.getString("mobilePhone")
        val role = db.getString("role")
        val id = db.reference.toString()
        user = ProfileData(
            id = id,
            firstName = firstName!!,
            secondName = secondName!!,
            mobilePhone = mobilePhone!!,
            role = role!!
        )
        return Resource.Success(user)
    }

    override suspend fun isAdmin(): Boolean {
        val firebaseUser = firebase.getFirebaseAuth().currentUser
        val db = firebase.getFirebaseFirestoreCollection("users").document(firebaseUser!!.uid).get()
            .await()

        val role = db.getString("role")
        if(role!! != "admin") {
            return false
        }
        return true
    }

    override suspend fun checkIfUserIsSignedIn(): Boolean {
        val firebaseUser = firebase.getFirebaseAuth().currentUser

        if (firebaseUser != null) {
            return true
        }

        return false
    }

    override suspend fun logout(): Boolean {
        val firebaseUser = firebase.getFirebaseAuth().currentUser

        if (firebaseUser != null) {
            firebase.getFirebaseAuth().signOut()
            return true;
        }
        return false;
    }

}

