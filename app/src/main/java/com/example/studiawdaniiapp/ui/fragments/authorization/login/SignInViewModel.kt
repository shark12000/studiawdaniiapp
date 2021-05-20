package com.example.studiawdaniiapp.ui.fragments.authorization.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studiawdaniiapp.domain.models.EmailPassword
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.domain.repository.IUserSignInRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(private val repository: IUserSignInRepo) : ViewModel() {

    private val mutableSignInLiveData = MutableLiveData<Resource<Boolean>>()
    val signInLiveData: LiveData<Resource<Boolean>> get() = mutableSignInLiveData

    private val mutableIsAdminLiveData = MutableLiveData<Boolean>()
    val isAdminLiveData: LiveData<Boolean> get() = mutableIsAdminLiveData

    fun signIn(emailPassword: EmailPassword) {
        viewModelScope.launch(Dispatchers.Default) {
            val result = repository.signIn(emailPassword)
            mutableSignInLiveData.postValue(result)
        }
    }

    fun isAdmin() {
        viewModelScope.launch {
            val result = repository.isAdmin()
            mutableIsAdminLiveData.postValue(result)
        }
    }
}