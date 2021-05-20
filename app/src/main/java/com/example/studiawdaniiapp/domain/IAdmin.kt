package com.example.studiawdaniiapp.domain

import com.example.studiawdaniiapp.domain.models.DataToReceive
import com.example.studiawdaniiapp.domain.models.Resource

interface IAdmin {
    suspend fun getSentData() : Resource<List<DataToReceive>>
    suspend fun setStatus(status: String, userID: String, documentID: String)
}