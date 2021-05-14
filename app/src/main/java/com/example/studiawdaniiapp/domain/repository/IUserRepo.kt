package com.example.studiawdaniiapp.domain.repository

import com.example.studiawdaniiapp.domain.models.ProfileData
import com.example.studiawdaniiapp.domain.models.Resource
import com.google.firebase.auth.FirebaseUser

interface IUserRepo {
    suspend fun getFirebaseUser(): Resource<FirebaseUser?>
    suspend fun getUser(): Resource<ProfileData>
    suspend fun isAdmin(): Boolean
    suspend fun checkIfUserIsSignedIn() : Boolean
    suspend fun logout(): Boolean
}