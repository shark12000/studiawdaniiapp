package com.example.studiawdaniiapp.domain.repository

import com.example.studiawdaniiapp.domain.models.DataToReceive
import com.example.studiawdaniiapp.domain.models.Resource

interface IAdminRepo {
    suspend fun getSentData() : Resource<List<DataToReceive>>
    suspend fun setStatus(status: String, userID: String, documentID: String)
}