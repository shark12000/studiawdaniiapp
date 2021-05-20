package com.example.studiawdaniiapp.domain.models

import com.google.firebase.firestore.DocumentId

data class DataToReceive(
    @DocumentId
    val id: String = "",
    val education: String = "",
    val programmes: List<Programme> = emptyList(),
    val userName: String = "",
    val userID: String = ""

)
