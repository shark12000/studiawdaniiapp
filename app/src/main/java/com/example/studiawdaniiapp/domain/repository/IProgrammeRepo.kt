package com.example.studiawdaniiapp.domain.repository

import com.example.studiawdaniiapp.domain.models.Programme
import com.example.studiawdaniiapp.domain.models.Resource

interface IProgrammeRepo{
    suspend fun getProgrammesId(universityId: String): List<String>
    suspend fun getProgramme(programmeID: String) : List<Programme>
}