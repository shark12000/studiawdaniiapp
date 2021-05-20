package com.example.studiawdaniiapp.ui.fragments.authorization.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studiawdaniiapp.domain.models.EmailPassword
import com.example.studiawdaniiapp.domain.models.Resource
import com.example.studiawdaniiapp.domain.repository.IUserRegistrationRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrationViewModel(private val repository: IUserRegistrationRepo) : ViewModel() {

    private val mutableRegistrationLiveData = MutableLiveData<Resource<Boolean>>()
    val registrationLiveData: LiveData<Resource<Boolean>> get() = mutableRegistrationLiveData

    fun registration(emailPassword: EmailPassword, repeatPassword: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if(emailPassword.password == repeatPassword) {
                val result = repository.registration(emailPassword)
                mutableRegistrationLiveData.postValue(result)
            } else {
                mutableRegistrationLiveData.postValue(Resource.Failure("passwords do not match"))
            }
        }
    }
}