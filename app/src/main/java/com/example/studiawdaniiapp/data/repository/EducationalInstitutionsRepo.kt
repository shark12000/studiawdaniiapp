package com.example.studiawdaniiapp.data.repository

import com.example.studiawdaniiapp.domain.models.EducationalInstitution
import com.example.studiawdaniiapp.domain.models.Programme
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.domain.repository.IEducationalInstitutionsRepo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class EducationalInstitutionsRepo : IEducationalInstitutionsRepo {

    override suspend fun getEducationalInstitutionsDB(): Resource<MutableList<EducationalInstitution>> {
        val list = mutableListOf<EducationalInstitution>()
        val db = FirebaseFirestore.getInstance().collection("university_info").get().await()
        for (document in db) {
            val educationalInstitutionName = document.getString("universityName")
            val id = document.id
            list.add(EducationalInstitution(id, educationalInstitutionName!!, ""))
        }

        return Resource.Success(list)
    }
}

