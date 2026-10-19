package com.example.studiawdaniiapp.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseSource(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    fun getFirebaseAuth(): FirebaseAuth {
        return auth
    }

    fun getFirebaseFirestoreCollection(): FirebaseFirestore {
        return firestore
    }
}
