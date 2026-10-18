package com.example.studiawdaniiapp.ui.fragments.home.lobby

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studiawdaniiapp.domain.models.ProfileData
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.domain.models.StepOneStatus
import com.example.studiawdaniiapp.domain.repository.IStatusRepo
import com.example.studiawdaniiapp.domain.repository.IUserRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LobbyViewModel(private val repository: IUserRepo, private val useCase: IStatusRepo, private val dispatcher: CoroutineDispatcher = Dispatchers.Default) : ViewModel() {

    private val mutableUserLiveData = MutableLiveData<Resource<ProfileData>>()
    val userLiveData: LiveData<Resource<ProfileData>> get() = mutableUserLiveData

    private val mutableStatusLiveData = MutableLiveData<Resource<StepOneStatus>>()
    val statusLiveData: LiveData<Resource<StepOneStatus>> get() = mutableStatusLiveData

    fun getStatus() {
        viewModelScope.launch(dispatcher) {
            val result = useCase.getStatus()
            mutableStatusLiveData.postValue(result)
        }
    }

    fun getUserProfileData() {
        viewModelScope.launch(dispatcher) {
            val result = repository.getUser()
            mutableUserLiveData.postValue(result)
        }
    }

    fun signOut() {
        viewModelScope.launch(dispatcher) {
            repository.logout()
        }
    }
}