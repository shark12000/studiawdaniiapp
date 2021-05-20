package com.example.studiawdaniiapp.ui.fragments.home.universityList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studiawdaniiapp.domain.IEducationalInstitutions
import com.example.studiawdaniiapp.domain.models.EducationalInstitution
import com.example.studiawdaniiapp.domain.models.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EduViewModel(private val useCase: IEducationalInstitutions) : ViewModel() {

    private val mutableEducationalInstitutionLiveData = MutableLiveData<Resource<List<EducationalInstitution>>>()
    val educationalInstitutionLiveData: LiveData<Resource<List<EducationalInstitution>>> get() = mutableEducationalInstitutionLiveData

    fun getEducationalInstitutions() {
        viewModelScope.launch(Dispatchers.Default) {
            val result = useCase.getEducationalInstitutions()
            mutableEducationalInstitutionLiveData.postValue(result)
        }
    }
}