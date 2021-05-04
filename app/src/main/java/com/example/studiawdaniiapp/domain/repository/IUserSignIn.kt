package com.example.studiawdaniiapp.domain.repository

import com.example.studiawdaniiapp.data.models.EmailPassword
import com.example.studiawdaniiapp.data.models.Resource
import com.google.firebase.auth.AuthResult

interface IUserSignIn {
    suspend fun signIn(emailPassword: EmailPassword): Resource<AuthResult>
}