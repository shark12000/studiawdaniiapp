package com.example.studiawdaniiapp.domain.models

import com.google.firebase.auth.AuthResult

data class AuthResult(
        val isAdmin: Boolean,
        val authorizationResult: Boolean
        )