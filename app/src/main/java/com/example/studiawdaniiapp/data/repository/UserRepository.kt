package com.example.studiawdaniiapp.data.repository

import androidx.lifecycle.LiveData
import com.example.studiawdaniiapp.data.LiveData.UserLiveData
import com.google.firebase.auth.FirebaseUser


class UserRepository() {
    private var currentUser: UserLiveData = UserLiveData()

    fun getCurrentUser(): LiveData<FirebaseUser> {
        return currentUser
    }

//    fun signOut() {
//        AuthUI.getInstance().signOut(app.applicationContext)
//    }

}
