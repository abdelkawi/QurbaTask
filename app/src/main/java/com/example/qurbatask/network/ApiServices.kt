package com.example.qurbatask.network

import com.example.qurbatask.network.entities.AuthRequest
import com.example.qurbatask.network.entities.AuthResponse
import com.example.qurbatask.network.entities.Request
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiServices {

    @POST(AUTH_END_POINT)
    suspend fun getJwt(@Body authRequest: AuthRequest):ApiResponse<AuthResponse>

    @POST
    suspend fun getNearbyList(@Body request: Request):ApiResponse<String>



}