package com.example.studiawdaniiapp.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseSource {
    fun getFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    fun getFirebaseFirestoreCollection(collection: String): CollectionReference {
        return FirebaseFirestore.getInstance().collection(collection)
    }
}