package com.example.studiawdaniiapp.domain.repository

import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.domain.models.StepOneStatus

interface IStatusRepo {
    suspend fun getStatus() : Resource<StepOneStatus>
    suspend fun setStatus(status: String)
}