package com.example.studiawdaniiapp.domain

import com.example.studiawdaniiapp.domain.models.DataToSend
import com.example.studiawdaniiapp.domain.models.Programme
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.domain.repository.IProgrammeApplicationRepo
import com.example.studiawdaniiapp.domain.repository.IProgrammeCatalogRepo

class ProgrammeImpl(
    private val catalog: IProgrammeCatalogRepo,
    private val application: IProgrammeApplicationRepo
) : IProgramme {

    override suspend fun getProgrammesByUniversityID(universityID: String): Resource<List<Programme>> {
        return catalog.getProgrammesByUniversityID(universityID)
    }

    override suspend fun getProgrammesByUserID(): Resource<List<Programme>> {
        return application.getProgrammesByUserID()
    }

    override suspend fun selectProgramme(programmeID: String) : Boolean {
        return application.selectProgramme(programmeID)
    }

    override suspend fun removeProgramme(programmeID: String) {
        return application.removeProgramme(programmeID)
    }

    override suspend fun sendData(dataToSend: DataToSend) : Resource<Boolean> {
        return application.sendData(dataToSend)
    }
}