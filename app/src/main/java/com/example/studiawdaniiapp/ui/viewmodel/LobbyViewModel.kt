package com.example.studiawdaniiapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.studiawdaniiapp.data.models.ProfileData
import com.example.studiawdaniiapp.data.models.Resource
import com.example.studiawdaniiapp.domain.repository.IUserRepo
import com.google.firebase.auth.FirebaseUser

class LobbyViewModel(private val repository: IUserRepo) : ViewModel() {

    fun getCurrentUser() = liveData<Resource<FirebaseUser?>> {
        emit(Resource.Loading())
        try {
            val result = repository.getFirebaseUser()
            emit(result)
        } catch (e: Exception) {
            emit(Resource.Failure(e.message.toString()))
        }
    }

    fun getUserProfileData() = liveData<Resource<ProfileData>> {
        emit(Resource.Loading())
        try {
            val result = repository.getUser()
            emit(result)
        } catch (e: Exception) {
            emit(Resource.Failure(e.message.toString()))
        }
    }
}