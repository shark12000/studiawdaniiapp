package com.example.studiawdaniiapp.domain.repository

import com.example.studiawdaniiapp.data.models.ProfileData
import com.example.studiawdaniiapp.data.models.Resource
import com.google.firebase.auth.FirebaseUser

interface IUserRepo {
    suspend fun getFirebaseUser(): Resource<FirebaseUser?>
    suspend fun getUser(): Resource<ProfileData>
}