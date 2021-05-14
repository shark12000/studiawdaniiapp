package com.example.studiawdaniiapp.domain.models

import com.google.firebase.firestore.DocumentId

data class Programme(
    @DocumentId
    val programmeId: String,
    val programmeName: String,
    val city: String,
)
