package com.example.studiawdaniiapp.data.models

import com.google.firebase.firestore.DocumentId

data class EducationalInstitution(
        @DocumentId
        val eduId: String,
        val educational_institution_name: String,
        val imageUrl: String
)



