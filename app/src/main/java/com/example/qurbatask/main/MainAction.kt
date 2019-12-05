package com.example.qurbatask.main

import com.example.qurbatask.mviBase.MviAction
import com.example.qurbatask.mviBase.MviResult
import com.example.qurbatask.network.RemoteDataSource
import com.example.qurbatask.network.Result
import com.example.qurbatask.network.entities.AuthRequest
import com.example.qurbatask.network.entities.AuthResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

sealed class MainAction : MviAction<MainResult>() {
    class Started
        (
        val authRequest: AuthRequest,
        val remoteDataSource: RemoteDataSource
    ) : MainAction() {
        override suspend fun invoke(): Flow<MainResult> {
            return flow {
                emit(MainResult.LoadingResult)
                when (val res = remoteDataSource.getJwt(authRequest)) {
                    is Result.Success -> emit(MainResult.SuccessResult(res.data))
                    is Result.Error -> {
                        emit(MainResult.ErrorResult(res.error.toString()))
                    }
                }

            }
        }
    }
}

sealed class MainResult : MviResult() {
    object LoadingResult : MainResult()
    data class SuccessResult(val data: AuthResponse) : MainResult()
    data class ErrorResult(val errorMsg: String) : MainResult()
}