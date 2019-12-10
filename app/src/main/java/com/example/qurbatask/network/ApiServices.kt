package com.example.qurbatask.network



import com.example.qurbatask.network.genericEntities.ApiResponse
import com.example.qurbatask.network.genericEntities.ApiRequest
import com.example.qurbatask.network.response.JWTPayload
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServices {

    @POST(AUTH_END_POINT)

    suspend fun getJwt(@Body authRequest: ApiRequest): ApiResponse<JWTPayload>

}