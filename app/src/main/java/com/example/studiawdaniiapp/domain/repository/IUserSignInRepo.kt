package com.example.studiawdaniiapp.domain.repository

import com.example.studiawdaniiapp.domain.models.AuthResult
import com.example.studiawdaniiapp.domain.models.EmailPassword
import com.example.studiawdaniiapp.domain.models.Resource

interface IUserSignInRepo {
    suspend fun signIn(emailPassword: EmailPassword): Resource<AuthResult>
}