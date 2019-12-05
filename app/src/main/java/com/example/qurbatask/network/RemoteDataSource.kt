package com.example.qurbatask.network

import com.example.qurbatask.network.entities.AuthRequest


class RemoteDataSource() {
    private val apiServices: ApiServices =
        NetworkConfig.createService(BASE_URL, true, ApiServices::class.java)

    suspend fun getJwt(authRequest: AuthRequest) =
        safeApiCall {
            apiServices.getJwt(authRequest)
        }

}