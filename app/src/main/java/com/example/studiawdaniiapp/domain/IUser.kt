package com.example.studiawdaniiapp.domain

import com.example.studiawdaniiapp.domain.models.ProfileData
import com.example.studiawdaniiapp.domain.models.Resource

interface IUser {
    suspend fun getUser(): Resource<ProfileData>
    suspend fun updateUser(profileData: ProfileData)
    suspend fun checkIfUserIsSignedIn() : Boolean
    suspend fun logout(): Boolean
}