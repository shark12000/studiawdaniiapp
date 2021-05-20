package com.example.studiawdaniiapp.domain

import android.util.Log
import com.example.studiawdaniiapp.domain.models.ProfileData
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.domain.repository.IUserRepo

class UserImpl(private val repository: IUserRepo) : IUser {

    override suspend fun getUser(): Resource<ProfileData> {
        return repository.getUser()
    }

    override suspend fun updateUser(profileData: ProfileData) {
        repository.updateUser(profileData)
    }

    override suspend fun checkIfUserIsSignedIn(): Boolean {
        return repository.checkIfUserIsSignedIn()
    }

    override suspend fun logout(): Boolean {
        return repository.logout()
    }
}