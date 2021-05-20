package com.example.studiawdaniiapp.domain.models

import com.google.firebase.firestore.DocumentId

data class EducationalInstitution(
        @DocumentId
        val eduId: String,
        val educationalInstitutionName: String,
        val description: String
)



