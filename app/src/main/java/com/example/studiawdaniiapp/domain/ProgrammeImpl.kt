package com.example.studiawdaniiapp.domain

import android.util.Log
import com.example.studiawdaniiapp.domain.models.Programme
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.domain.repository.IProgrammeRepo

class ProgrammeImpl(private val repository: IProgrammeRepo) : IProgramme {
    private val TAG = "ProgrammeImpl"

    override suspend fun getUniversityProgrammes(universityId: String): Resource<MutableList<Programme>> {
        val programmeList = mutableListOf<Programme>()
        val list = repository.getProgrammesId(universityId)
        for (programme in list) {
            Log.d(TAG, programme)
            val programmes = repository.getProgramme(programme)
            Log.d(TAG, programmes[0].programmeName)
            programmeList.addAll(programmes)
        }
        return Resource.Success(programmeList)
    }


}