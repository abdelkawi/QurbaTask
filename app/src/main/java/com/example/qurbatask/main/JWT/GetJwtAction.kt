package com.example.qurbatask.main.JWT

import com.example.qurbatask.mviBase.MviAction
import com.example.qurbatask.mviBase.MviResult
import com.example.qurbatask.network.RemoteDataSource
import com.example.qurbatask.network.Result
import com.example.qurbatask.network.genericEntities.ApiRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetJwtAction(
    val authRequest: ApiRequest,
    val remoteDataSource: RemoteDataSource
) : MviAction<GetJWTResult>() {
    override suspend fun invoke(): Flow<GetJWTResult> {
        return flow {
            emit(GetJWTResult.LoadingResult)
            when (val res = remoteDataSource.getJwt(authRequest)) {
                is Result.Success -> {
                    if (res.data.jwt != null)
                        emit(
                            GetJWTResult.SuccessResult(
                                res.data.jwt
                            )
                        )
                }
                is Result.Error -> {
                    emit(
                        GetJWTResult.ErrorResult(
                            res.error.toString()
                        )
                    )
                }
            }

        }
    }
}

sealed class GetJWTResult : MviResult() {
    object LoadingResult : GetJWTResult()
    data class SuccessResult(val jwt: String) : GetJWTResult()
    data class ErrorResult(val errorMsg: String) : GetJWTResult()
}