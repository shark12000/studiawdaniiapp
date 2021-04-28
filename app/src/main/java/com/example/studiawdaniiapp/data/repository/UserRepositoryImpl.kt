package com.example.studiawdaniiapp.data.repository

import com.example.studiawdaniiapp.data.firebase.FirebaseSource
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Completable


class UserRepositoryImpl(private val firebase: FirebaseSource) : UserRepository {

    override fun login(email: String, password: String): Completable {
       return firebase.login(email, password)
    }

    override fun register(email: String, password: String): Completable {
        return firebase.register(email, password)
    }

    override fun currentUser(): FirebaseUser? {
        return firebase.currentUser()
    }

    override fun logout() = firebase.logout()

}

