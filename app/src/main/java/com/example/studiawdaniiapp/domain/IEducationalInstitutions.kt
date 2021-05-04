package com.example.studiawdaniiapp.domain

import com.example.studiawdaniiapp.data.models.EducationalInstitution
import com.example.studiawdaniiapp.data.models.Resource

interface IEducationalInstitutions {
    suspend fun getEducationalInstitutions(): Resource<MutableList<EducationalInstitution>>
}