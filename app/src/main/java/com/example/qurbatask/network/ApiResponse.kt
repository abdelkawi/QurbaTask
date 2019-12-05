package com.example.qurbatask.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse<T>(
    val Success: Boolean?,
    val Code: Int?,
    val EnglishMessage: String?,
    val ArabicMessage: String?,
    val Data: T?,
    val ServiceName: String?,
    val IsReveal: Boolean?,
    val ShowReveal: Boolean?
)