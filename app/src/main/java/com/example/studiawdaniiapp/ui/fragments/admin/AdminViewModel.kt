package com.example.studiawdaniiapp.ui.fragments.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studiawdaniiapp.domain.IAdmin
import com.example.studiawdaniiapp.domain.IUser
import com.example.studiawdaniiapp.domain.models.DataToReceive
import com.example.studiawdaniiapp.domain.models.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdminViewModel(private val useCase: IAdmin, private val useCaseUser: IUser, private val dispatcher: CoroutineDispatcher = Dispatchers.Default) : ViewModel() {

    private val mutableDataLiveData = MutableLiveData<Resource<List<DataToReceive>>>()
    val dataLiveData: LiveData<Resource<List<DataToReceive>>> get() = mutableDataLiveData

    fun getAdminData() {
        viewModelScope.launch(dispatcher) {
            val result = useCase.getSentData()
            mutableDataLiveData.postValue(result)
        }
    }

    fun setStatus(status: String, userID: String, documentID: String) {
        viewModelScope.launch(dispatcher) {
            useCase.setStatus(status, userID, documentID)
        }
    }

    fun signOut() {
        viewModelScope.launch(dispatcher) {
            useCaseUser.logout()
        }
    }
}