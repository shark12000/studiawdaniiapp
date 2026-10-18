package com.example.studiawdaniiapp.ui.fragments.authorization.registrationData

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studiawdaniiapp.domain.IUser
import com.example.studiawdaniiapp.domain.models.ProfileData
import com.example.studiawdaniiapp.domain.models.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrationDataViewModel(private val useCase: IUser, private val dispatcher: CoroutineDispatcher = Dispatchers.Default) : ViewModel() {
    private val mutableProfileLiveData = MutableLiveData<Resource<ProfileData>>()
    val profileLiveData: LiveData<Resource<ProfileData>> get() = mutableProfileLiveData

    fun updateProfileData(firstName: String, lastName: String, mobilePhone: String) {
        viewModelScope.launch(dispatcher) {

            val profileData = ProfileData(
                firstName = firstName,
                secondName = lastName,
                mobilePhone = mobilePhone,
                id = null,
                role = "user",
                email = null
            )

            useCase.updateUser(profileData)
        }
    }

    fun getUser() {
        viewModelScope.launch(dispatcher) {
            val result = useCase.getUser()
            mutableProfileLiveData.postValue(result)
        }
    }
}