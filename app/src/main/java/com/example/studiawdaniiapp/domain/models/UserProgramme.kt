package com.example.studiawdaniiapp.domain.models

import com.google.firebase.firestore.DocumentId

data class UserProgramme(
    val userID: String,
    val programmeID: String
)
