package com.example.qurbatask.network.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthRequest(
    @Json(name="payload")
    val payload: Payload? = null
)
