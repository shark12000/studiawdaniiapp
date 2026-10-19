package com.example.studiawdaniiapp.data.repository

import com.example.studiawdaniiapp.data.firebase.FirebaseSource
import com.example.studiawdaniiapp.domain.models.EducationalInstitution
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.domain.repository.IEducationalInstitutionsRepo
import kotlinx.coroutines.tasks.await

class EducationalInstitutionsRepo(private val firebaseSource: FirebaseSource) : IEducationalInstitutionsRepo {

    companion object{
        private const val UNIVERSITY_COLLECTION = "university_info"
        private const val UNIVERSITY_NAME = "universityName"
        private const val UNIVERSITY_DESCRIPTION = "description"
    }

    override suspend fun getEducationalInstitutionsDB(): Resource<MutableList<EducationalInstitution>> {
        val list = mutableListOf<EducationalInstitution>()
        val db = firebaseSource.getFirebaseFirestoreCollection().collection(UNIVERSITY_COLLECTION).get().await()
        for (document in db) {
            val educationalInstitutionName = document.getString(UNIVERSITY_NAME)!!
            val description = document.getString(UNIVERSITY_DESCRIPTION)!!
            val id = document.id
            list.add(EducationalInstitution(id, educationalInstitutionName, description))
        }

        return Resource.Success(list)
    }
}

