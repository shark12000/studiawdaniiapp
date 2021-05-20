package com.example.studiawdaniiapp.domain.models

import com.google.firebase.firestore.DocumentId

data class Programme(
    @DocumentId
    val programmeID: String,
    val universityID: String,
    val programmeName: String,
    val city: String,
    val programmeType: String,
    val programmeLength: String,
    val description: String
)
