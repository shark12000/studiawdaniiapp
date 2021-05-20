package com.example.studiawdaniiapp.domain.models

import com.google.firebase.firestore.DocumentId

data class ProfileData(
    @DocumentId
    val id: String?,
    val firstName: String,
    val secondName: String,
    val mobilePhone: String,
    val email: String?,
    val role: String
)