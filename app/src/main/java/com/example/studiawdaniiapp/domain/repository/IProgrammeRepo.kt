package com.example.studiawdaniiapp.domain.repository

import com.example.studiawdaniiapp.domain.models.DataToSend
import com.example.studiawdaniiapp.domain.models.Programme
import com.example.studiawdaniiapp.domain.models.Resource

interface IProgrammeRepo{
    suspend fun getProgrammesByUniversityID(universityID: String) : Resource<List<Programme>>
    suspend fun getProgrammesByUserID() : Resource<List<Programme>>
    suspend fun selectProgramme(programmeID: String) : Boolean
    suspend fun removeProgramme(programmeID: String)
    suspend fun sendData(dataToSend: DataToSend) : Resource<Boolean>
}