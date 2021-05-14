package com.example.studiawdaniiapp.ui.fragments.home.universityInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.studiawdaniiapp.domain.models.Programme
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.domain.IProgramme

class UniversityInfoViewModel(private val useCase: IProgramme): ViewModel() {

    fun getUniversityInfo(id: String) = liveData<Resource<MutableList<Programme>>> {
        try {
            val result = useCase.getUniversityProgrammes(id);
            emit(result)
        } catch (e: Exception) {
            emit(Resource.Failure(e.message.toString()))
        }
    }

}