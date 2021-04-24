package com.example.studiawdaniiapp.data.LiveData

import androidx.lifecycle.LiveData

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class UserLiveData() : LiveData<FirebaseUser>() {

    private var listener: FirebaseAuth.AuthStateListener = FirebaseAuth.AuthStateListener { firebaseAuth -> value = firebaseAuth.currentUser };

    override fun onActive() {
        super.onActive()
        FirebaseAuth.getInstance().addAuthStateListener(listener)
    }

    override fun onInactive() {
        super.onInactive()
        FirebaseAuth.getInstance().removeAuthStateListener(listener)
    }
}