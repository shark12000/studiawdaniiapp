package com.example.studiawdaniiapp.domain.repository

import com.example.studiawdaniiapp.domain.models.Programme
import com.example.studiawdaniiapp.domain.models.Resource

interface IProgrammeCatalogRepo {
    suspend fun getProgrammesByUniversityID(universityID: String) : Resource<List<Programme>>
    suspend fun getProgrammeByID(programmeID: String) : Programme
}
