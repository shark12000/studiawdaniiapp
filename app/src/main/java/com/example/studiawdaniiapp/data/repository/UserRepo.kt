package com.example.studiawdaniiapp.data.repository

import com.example.studiawdaniiapp.data.firebase.FirebaseSource
import com.example.studiawdaniiapp.domain.models.ProfileData
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.domain.repository.IUserRepo
import kotlinx.coroutines.tasks.await


class UserRepo(private val firebase: FirebaseSource) : IUserRepo {
    private var user: ProfileData? = null

    override suspend fun getUser(): Resource<ProfileData> {
        try {
            if(user==null) {
                val firebaseUser = firebase.getFirebaseAuth().currentUser
                val db = firebase.getFirebaseFirestoreCollection().collection("users")
                    .document(firebaseUser!!.uid).get()
                    .await()

                val firstName = db.getString("firstName")
                val secondName = db.getString("secondName")
                val mobilePhone = db.getString("mobilePhone")
                val role = db.getString("role")
                val email = firebaseUser.email
                val id = firebaseUser.uid

                user = ProfileData(
                    id = id,
                    firstName = firstName!!,
                    secondName = secondName!!,
                    mobilePhone = mobilePhone!!,
                    role = role!!,
                    email = email!!
                )
            }
            return Resource.Success(user!!)
        } catch (e: Exception) {
            return Resource.Failure(e.message.toString())
        }
    }

    override suspend fun updateUser(profileData: ProfileData) {
        val user = firebase.getFirebaseAuth().currentUser;
        val ref =
            firebase.getFirebaseFirestoreCollection().collection("users").document(user!!.uid)
        val profile = ProfileData(
            mobilePhone = profileData.mobilePhone,
            firstName = profileData.firstName,
            secondName = profileData.secondName,
            role = profileData.role,
            email = user.email!!,
            id = user.uid)
        ref.set(profile).await()
    }

    override suspend fun checkIfUserIsSignedIn(): Boolean {
        val firebaseUser = firebase.getFirebaseAuth().currentUser

        if (firebaseUser != null) {
            return true
        }

        return false
    }

    override suspend fun logout(): Boolean {
        user = null
        val firebaseUser = firebase.getFirebaseAuth().currentUser

        if (firebaseUser != null) {
            firebase.getFirebaseAuth().signOut()
            return true;
        }

        return false;
    }

}

