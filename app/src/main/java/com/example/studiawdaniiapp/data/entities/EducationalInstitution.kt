package com.example.studiawdaniiapp.data.entities

data class EducationalInstitution(

        val educational_institution_name: String,

        val programmes: Collection<Programme>,

        val education_levels: Collection<EducationType>,

        val imageUrl: String
)
