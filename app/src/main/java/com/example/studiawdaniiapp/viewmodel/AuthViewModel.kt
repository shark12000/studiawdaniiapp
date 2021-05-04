package com.example.studiawdaniiapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.studiawdaniiapp.data.repository.UserRepositoryImpl
import com.example.studiawdaniiapp.ui.AuthListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

class AuthViewModel : ViewModel(), KoinComponent {

    private val userRepositoryImpl: UserRepositoryImpl by inject()

    var authListener: AuthListener? = null

    private val disposables = CompositeDisposable()

    val user by lazy {
        userRepositoryImpl.currentUser()
    }

    fun login(email: String?, password: String?)  {

        //validating email and password
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
        }

        //authentication started
        authListener?.onStarted()

        val disposable = userRepositoryImpl.login(email!!, password!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //sending a success callback
                authListener?.onSuccess("You are in the system")
            }, {
                //sending a failure callback
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

    //Doing same thing with signup
    fun signup(email: String?, password: String?) {
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Please input all values")
            return
        }

        authListener?.onStarted()

        val disposable = userRepositoryImpl.register(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authListener?.onSuccess("User successfully registered")
            }, {
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

    //disposing the disposables
    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

}