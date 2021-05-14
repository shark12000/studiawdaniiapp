package com.example.studiawdaniiapp.domain.models

sealed class Resource<out T> {
    class Loading<out T> : Resource<T>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure<out T>(val string: String) : Resource<T>()
}
