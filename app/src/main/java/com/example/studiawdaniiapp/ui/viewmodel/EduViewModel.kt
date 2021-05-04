package com.example.studiawdaniiapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.studiawdaniiapp.data.models.EducationalInstitution
import com.example.studiawdaniiapp.data.models.Resource
import com.example.studiawdaniiapp.domain.IEducationalInstitutions
import kotlinx.coroutines.Dispatchers


class EduViewModel(private val useCase: IEducationalInstitutions) : ViewModel() {


    val fetchEducationalInstitutionsList =
        liveData<Resource<MutableList<EducationalInstitution>>>(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val educationalInstitutionsList = useCase.getEducationalInstitutions()
                emit(educationalInstitutionsList)
            } catch (e: Exception) {
                emit(Resource.Failure(e.message.toString()))
            }
        }


}