package com.example.studiawdaniiapp.data.repository

import com.example.studiawdaniiapp.data.firebase.FirebaseSource
import com.example.studiawdaniiapp.data.models.ProfileData
import com.example.studiawdaniiapp.data.models.Resource
import com.example.studiawdaniiapp.domain.repository.IProfileRepo
import kotlinx.coroutines.tasks.await

class ProfileRepo(private val firebase: FirebaseSource) : IProfileRepo {
    override suspend fun updateProfile(profileData: ProfileData): Resource<Void?> {
        val user = firebase.getFirebaseAuth().currentUser;
        val ref = firebase.getFirebaseFirestoreCollection("users").document(user!!.uid)
        val result = ref.set(
            ProfileData(
                mobilePhone = profileData.mobilePhone,
                firstName = profileData.firstName,
                secondName = profileData.secondName,
                sex = profileData.sex,
                id = user.uid
            )
        ).await()
        return Resource.Success(result)
    }
}