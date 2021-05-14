package com.example.studiawdaniiapp.ui.fragments.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.studiawdaniiapp.domain.models.ProfileData
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.domain.repository.IProfileRepo
import com.example.studiawdaniiapp.domain.repository.IUserRepo
import com.google.firebase.auth.FirebaseUser
import java.lang.Exception

class ProfileViewModel(
    private val repository: IProfileRepo,
    private val userRepository: IUserRepo
) : ViewModel() {
    fun getCurrentUser() = liveData<Resource<FirebaseUser?>> {
        emit(Resource.Loading())
        try {
            val result = userRepository.getFirebaseUser()
            emit(result)
        } catch (e: Exception) {
            emit(Resource.Failure(e.message.toString()))
        }
    }

    fun getUserProfileData() = liveData<Resource<ProfileData>> {
        emit(Resource.Loading())
        try {
            val result = userRepository.getUser()
            emit(result)
        } catch (e: Exception) {
            emit(Resource.Failure(e.message.toString()))
        }
    }
}