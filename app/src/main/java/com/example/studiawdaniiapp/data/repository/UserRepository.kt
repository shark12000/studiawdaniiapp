package com.example.studiawdaniiapp.data.repository

import com.google.firebase.auth.FirebaseUser
import io.reactivex.Completable

interface UserRepository {
    fun login(email: String, password: String): Completable
    fun register(email: String, password: String) : Completable
    fun currentUser(): FirebaseUser?
    fun logout()
}