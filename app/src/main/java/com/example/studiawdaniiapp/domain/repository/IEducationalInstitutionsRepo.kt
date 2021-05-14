package com.example.studiawdaniiapp.domain.repository

import com.example.studiawdaniiapp.domain.models.EducationalInstitution
import com.example.studiawdaniiapp.domain.models.Resource

interface IEducationalInstitutionsRepo {
    suspend fun getEducationalInstitutionsDB(): Resource<MutableList<EducationalInstitution>>
}