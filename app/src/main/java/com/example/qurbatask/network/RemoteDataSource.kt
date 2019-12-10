package com.example.qurbatask.network


import com.example.qurbatask.network.genericEntities.ApiRequest
import com.example.qurbatask.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class RemoteDataSource {
    private val apiServices: ApiServices =
        NetworkConfig.createService(BASE_URL, true, ApiServices::class.java)

    suspend fun getJwt(authRequest: ApiRequest) = withContext(Dispatchers.IO) {
        safeApiCall {
            apiServices.getJwt(authRequest)
        }
    }

}