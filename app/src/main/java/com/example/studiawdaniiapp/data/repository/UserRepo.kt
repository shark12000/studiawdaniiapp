package com.example.studiawdaniiapp.data.repository

import com.example.studiawdaniiapp.data.firebase.FirebaseSource
import com.example.studiawdaniiapp.data.models.ProfileData
import com.example.studiawdaniiapp.data.models.Resource
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
        val sex = db.getString("sex")
        val mobilePhone = db.getString("mobilePhone")
        val id = db.reference.toString()
        user = ProfileData(
            id = id,
            firstName = firstName!!,
            secondName = secondName!!,
            sex = sex!!,
            mobilePhone = mobilePhone!!,
        )
        return Resource.Success(user)
    }

}

