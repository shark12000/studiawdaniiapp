package com.example.studiawdaniiapp.domain.repository

import com.example.studiawdaniiapp.data.models.EmailPassword
import com.example.studiawdaniiapp.data.models.Resource
import com.google.firebase.auth.AuthResult

interface IUserRegistrationRepo {
    suspend fun sendEmailVerification(): Resource<Void?>
    suspend fun registration(emailPassword: EmailPassword): Resource<AuthResult>
}