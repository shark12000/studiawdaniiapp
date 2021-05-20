package com.example.studiawdaniiapp.ui.fragments.home.universityInfo

import android.util.Log
import androidx.lifecycle.*
import com.example.studiawdaniiapp.domain.IProgramme
import com.example.studiawdaniiapp.domain.models.Programme
import com.example.studiawdaniiapp.domain.models.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UniversityInfoViewModel(private val stateHandle: SavedStateHandle, private val useCase: IProgramme): ViewModel() {

    companion object {
        private const val PROGRAMMES = "programmes"
        private const val INTS = "ints"
        private const val BOOLEANS = "booleans"
        private const val TAG = "UIVM"
    }

    private val mutableProgrammeLiveData = MutableLiveData<Resource<List<Programme>>>(stateHandle.get(PROGRAMMES))
    val programmeLiveData: LiveData<Resource<List<Programme>>> get() = mutableProgrammeLiveData

    private val mutableBooleanLiveData = MutableLiveData<Boolean>(stateHandle.get(BOOLEANS))
    val booleanLiveData: LiveData<Boolean> get() = mutableBooleanLiveData

    private val mutableIntLiveData = MutableLiveData<Int>(stateHandle.get(INTS))
    val intLiveData: LiveData<Int> get() = mutableIntLiveData

    override fun onCleared() {
        super.onCleared()
        stateHandle.set(BOOLEANS, mutableBooleanLiveData.value)
        stateHandle.set(INTS, mutableIntLiveData.value)
    }

    fun getUserProfileData(universityID: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val result = useCase.getProgrammesByUniversityID(universityID)
            mutableProgrammeLiveData.postValue(result)
        }
    }

    fun selectProgramme(programmeID: String) {
        viewModelScope.launch(Dispatchers.Default) {
           val result = useCase.selectProgramme(programmeID)
            Log.d(TAG, result.toString())
            mutableBooleanLiveData.postValue(result)
        }
    }

}