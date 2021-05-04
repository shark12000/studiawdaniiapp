package com.example.studiawdaniiapp.domain

import com.example.studiawdaniiapp.data.models.EducationalInstitution
import com.example.studiawdaniiapp.data.models.Resource
import com.example.studiawdaniiapp.domain.repository.IEducationalInstitutionsRepo

class EducationalInstitutionsImpl(private val repository: IEducationalInstitutionsRepo) :
    IEducationalInstitutions {

    override suspend fun getEducationalInstitutions(): Resource<MutableList<EducationalInstitution>> {
        return repository.getEducationalInstitutionsDB()
    }
}