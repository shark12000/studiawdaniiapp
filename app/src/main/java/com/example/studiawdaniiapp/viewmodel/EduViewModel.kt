package com.example.studiawdaniiapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studiawdaniiapp.data.models.EducationalInstitution
import com.example.studiawdaniiapp.data.repository.EduRepository
import com.example.studiawdaniiapp.ui.Test

class EduViewModel : ViewModel(), EduRepository.OnFirestoreTaskComplete {

    private val eduListData : MutableLiveData<List<EducationalInstitution>> = MutableLiveData()

    private val eduRepository: EduRepository = EduRepository(this)

    var testListener: Test? = null

    init {
        eduRepository.getUniData()
    }

    override fun eduDataAdded(eduList: List<EducationalInstitution>) {
        eduListData.value = eduList
        testListener?.onSuccess(eduListData.value.toString())
    }

    override fun onError(e: Exception) {
        testListener?.onFailure(e.toString())
    }

    fun getEduListData(): LiveData<List<EducationalInstitution>> {
        return eduListData
    }
}