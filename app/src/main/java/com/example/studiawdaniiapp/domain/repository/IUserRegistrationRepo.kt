package com.example.studiawdaniiapp.domain.repository

import com.example.studiawdaniiapp.domain.models.EmailPassword
import com.example.studiawdaniiapp.domain.models.Resource

interface IUserRegistrationRepo {
    suspend fun registration(emailPassword: EmailPassword): Resource<Boolean>
}