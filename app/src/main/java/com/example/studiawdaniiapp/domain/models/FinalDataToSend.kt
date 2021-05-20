package com.example.studiawdaniiapp.domain.models

data class FinalDataToSend(
    val dataToSend: DataToSend,
    val userID: String,
    val answered: Boolean
)
