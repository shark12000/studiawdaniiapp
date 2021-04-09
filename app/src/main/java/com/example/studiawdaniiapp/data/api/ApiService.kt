package com.example.studiawdaniiapp.data.api

import com.example.studiawdaniiapp.data.model.User
import io.reactivex.Single

interface ApiService {
    fun getUsers(): Single<List<User>>
}