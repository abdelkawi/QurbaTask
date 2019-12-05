package com.example.qurbatask.network.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Request(
    val lng: String,
    val lat: String
)