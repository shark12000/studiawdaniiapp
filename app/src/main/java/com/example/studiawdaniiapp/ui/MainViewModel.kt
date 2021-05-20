package com.example.studiawdaniiapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.studiawdaniiapp.domain.repository.IUserRepo

class MainViewModel(private val repository: IUserRepo) : ViewModel(){

    fun getCurrentUser() = liveData<Boolean> {
        val result: Boolean = repository.checkIfUserIsSignedIn()
        emit(result)
    }
}