package com.example.studiawdaniiapp.domain.repository

import com.example.studiawdaniiapp.domain.models.ProfileData
import com.example.studiawdaniiapp.domain.models.Resource

interface IProfileRepo {
    suspend fun updateProfile(profileData: ProfileData): Resource<Void?>
}