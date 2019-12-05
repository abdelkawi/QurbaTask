package com.example.qurbatask.network

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val error: Failure) : Result<T>()
}
