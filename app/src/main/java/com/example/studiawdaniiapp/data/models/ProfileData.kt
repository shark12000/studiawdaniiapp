package com.example.studiawdaniiapp.data.models

import com.google.firebase.firestore.DocumentId

data class ProfileData(
    @DocumentId
    val id: String?,
    val firstName: String,
    val secondName: String,
    val mobilePhone: String,
    val sex: String,
)