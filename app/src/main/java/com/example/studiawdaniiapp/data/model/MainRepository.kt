package com.example.studiawdaniiapp.data.model

import com.example.studiawdaniiapp.data.api.ApiHelper
import io.reactivex.Single

class MainRepository(private val apiHelper: ApiHelper) {

    fun getUsers(): Single<List<User>> {
        return apiHelper.getUsers()
    }
}