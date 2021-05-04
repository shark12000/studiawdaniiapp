package com.example.studiawdaniiapp.data.repository

import com.example.studiawdaniiapp.data.models.EducationalInstitution
import com.example.studiawdaniiapp.data.models.Resource
import com.example.studiawdaniiapp.domain.repository.IEducationalInstitutionsRepo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class EducationalInstitutionsRepo : IEducationalInstitutionsRepo {

    override suspend fun getEducationalInstitutionsDB(): Resource<MutableList<EducationalInstitution>> {
        val list = mutableListOf<EducationalInstitution>()
        val db = FirebaseFirestore.getInstance().collection("university_info").get().await()
        for (document in db) {
            val educationalInstitutionName = document.getString("educational_institution_name")
            val imageUrl = document.getString("imageUrl")
            val id = document.reference.toString()
            list.add(EducationalInstitution(id, educationalInstitutionName!!, imageUrl!!))
        }

        return Resource.Success(list)
    }
}

