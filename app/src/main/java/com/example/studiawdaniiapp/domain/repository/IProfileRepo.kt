package com.example.studiawdaniiapp.domain.repository

import com.example.studiawdaniiapp.data.models.ProfileData
import com.example.studiawdaniiapp.data.models.Resource

interface IProfileRepo {
    suspend fun updateProfile(profileData: ProfileData): Resource<Void?>
}