package com.example.studiawdaniiapp.domain

import com.example.studiawdaniiapp.domain.models.EducationalInstitution
import com.example.studiawdaniiapp.domain.models.Resource

interface IEducationalInstitutions {
    suspend fun getEducationalInstitutions(): Resource<MutableList<EducationalInstitution>>
}