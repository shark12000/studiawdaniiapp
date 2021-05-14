package com.example.studiawdaniiapp.domain

import com.example.studiawdaniiapp.domain.models.Programme
import com.example.studiawdaniiapp.domain.models.Resource

interface IProgramme {
    suspend fun getUniversityProgrammes(universityId : String) : Resource<MutableList<Programme>>
}