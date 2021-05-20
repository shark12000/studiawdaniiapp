package com.example.studiawdaniiapp.domain

import com.example.studiawdaniiapp.domain.models.DataToSend
import com.example.studiawdaniiapp.domain.models.Programme
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.domain.repository.IProgrammeRepo

class ProgrammeImpl(private val repository: IProgrammeRepo) : IProgramme {

    override suspend fun getProgrammesByUniversityID(universityID: String): Resource<List<Programme>> {
        return repository.getProgrammesByUniversityID(universityID)
    }

    override suspend fun getProgrammesByUserID(): Resource<List<Programme>> {
        return repository.getProgrammesByUserID()
    }

    override suspend fun selectProgramme(programmeID: String) : Boolean {
        return repository.selectProgramme(programmeID)
    }

    override suspend fun removeProgramme(programmeID: String) {
        return repository.removeProgramme(programmeID)
    }

    override suspend fun sendData(dataToSend: DataToSend) : Resource<Boolean> {
        return repository.sendData(dataToSend)
    }
}