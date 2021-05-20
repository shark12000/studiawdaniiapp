package com.example.studiawdaniiapp.domain

import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.domain.models.StepOneStatus
import com.example.studiawdaniiapp.domain.repository.IStatusRepo

class StatusImpl(private val repository: IStatusRepo) : IStatus {
    override suspend fun getStatus(): Resource<StepOneStatus> {
        return repository.getStatus()
    }

    override suspend fun setStatus(status: String) {
       repository.setStatus(status)
    }
}