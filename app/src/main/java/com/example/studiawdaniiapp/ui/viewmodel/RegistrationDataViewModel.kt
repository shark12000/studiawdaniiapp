package com.example.studiawdaniiapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.studiawdaniiapp.data.models.ProfileData
import com.example.studiawdaniiapp.data.models.Resource
import com.example.studiawdaniiapp.domain.repository.IProfileRepo

class RegistrationDataViewModel(private val repository: IProfileRepo) : ViewModel() {

    fun addData(profileData: ProfileData) = liveData<Resource<Void?>> {
        emit(Resource.Loading())
        try {
            val result = repository.updateProfile(profileData)
            emit(result)
        } catch (e: Exception) {
            emit(Resource.Failure(e.message.toString()))
        }
    }
}