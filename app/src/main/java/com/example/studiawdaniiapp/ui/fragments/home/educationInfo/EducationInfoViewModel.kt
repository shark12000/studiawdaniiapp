package com.example.studiawdaniiapp.ui.fragments.home.educationInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studiawdaniiapp.domain.IProgramme
import com.example.studiawdaniiapp.domain.IStatus
import com.example.studiawdaniiapp.domain.models.DataToSend
import com.example.studiawdaniiapp.domain.models.Programme
import com.example.studiawdaniiapp.domain.models.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EducationInfoViewModel(private val useCase: IProgramme, private val useCaseStatus: IStatus) : ViewModel() {

    private val mutableEducationLiveData = MutableLiveData<Resource<List<Programme>>>()
    val educationLiveData: LiveData<Resource<List<Programme>>> get() = mutableEducationLiveData

    private val mutableBooleanLiveData = MutableLiveData<Resource<Boolean>>()
    val booleanLiveData: LiveData<Resource<Boolean>> get() = mutableBooleanLiveData

    fun getProgrammeListData() {
        viewModelScope.launch(Dispatchers.Default) {
            val result = useCase.getProgrammesByUserID()
            mutableEducationLiveData.postValue(result)
        }
    }

    fun removeItem(programmeID: String) {
        viewModelScope.launch(Dispatchers.Default) {
            useCase.removeProgramme(programmeID)
        }
    }

    fun setStatus(status: String) {
        viewModelScope.launch(Dispatchers.Default) {
            useCaseStatus.setStatus(status)
        }
    }

    fun sendData(dataToSend: DataToSend) {
        viewModelScope.launch(Dispatchers.Default) {
            val result = useCase.sendData(dataToSend)
            mutableBooleanLiveData.postValue(result)
        }
    }
}