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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EducationInfoViewModel(private val useCase: IProgramme, private val useCaseStatus: IStatus, private val dispatcher: CoroutineDispatcher = Dispatchers.Default) : ViewModel() {

    private val mutableEducationLiveData = MutableLiveData<Resource<List<Programme>>>()
    val educationLiveData: LiveData<Resource<List<Programme>>> get() = mutableEducationLiveData

    private val mutableBooleanLiveData = MutableLiveData<Resource<Boolean>>()
    val booleanLiveData: LiveData<Resource<Boolean>> get() = mutableBooleanLiveData

    fun getProgrammeListData() {
        viewModelScope.launch(dispatcher) {
            val result = useCase.getProgrammesByUserID()
            mutableEducationLiveData.postValue(result)
        }
    }

    fun removeItem(programmeID: String) {
        viewModelScope.launch(dispatcher) {
            useCase.removeProgramme(programmeID)
        }
    }

    fun setStatus(status: String) {
        viewModelScope.launch(dispatcher) {
            useCaseStatus.setStatus(status)
        }
    }

    fun sendData(dataToSend: DataToSend) {
        viewModelScope.launch(dispatcher) {
            val result = useCase.sendData(dataToSend)
            mutableBooleanLiveData.postValue(result)
        }
    }
}