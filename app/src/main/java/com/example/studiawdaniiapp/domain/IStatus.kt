package com.example.studiawdaniiapp.domain

import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.domain.models.StepOneStatus

interface IStatus {
    suspend fun getStatus() : Resource<StepOneStatus>
    suspend fun setStatus(status: String)
}