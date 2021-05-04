package com.example.studiawdaniiapp.domain.repository

import com.example.studiawdaniiapp.data.models.EducationalInstitution
import com.example.studiawdaniiapp.data.models.Resource

interface IEducationalInstitutionsRepo {
    suspend fun getEducationalInstitutionsDB(): Resource<MutableList<EducationalInstitution>>
}