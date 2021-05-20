package com.example.studiawdaniiapp.ui.fragments.home.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studiawdaniiapp.domain.models.ProfileData
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.domain.repository.IUserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: IUserRepo
) : ViewModel() {

    private val mutableUserLiveData = MutableLiveData<Resource<ProfileData>>()
    val userLiveData: LiveData<Resource<ProfileData>> get() = mutableUserLiveData

    fun getUserProfileData() {
        viewModelScope.launch(Dispatchers.Default) {
            val result = repository.getUser()
            mutableUserLiveData.postValue(result)
        }
    }
}