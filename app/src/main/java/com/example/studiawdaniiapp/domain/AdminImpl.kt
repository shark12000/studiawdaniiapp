package com.example.studiawdaniiapp.domain

import com.example.studiawdaniiapp.domain.models.DataToReceive
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.domain.repository.IAdminRepo

class AdminImpl(private val repository: IAdminRepo) : IAdmin {
    override suspend fun getSentData() : Resource<List<DataToReceive>> {
        return repository.getSentData()
    }

    override suspend fun setStatus(status: String, userID: String, documentID: String) {
        repository.setStatus(status, userID, documentID)
    }
}