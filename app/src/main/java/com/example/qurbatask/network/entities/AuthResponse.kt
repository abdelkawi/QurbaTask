package com.example.qurbatask.network.entities

import com.squareup.moshi.Json

data class AuthResponse(

	@Json(name="payload")
	val payload: Payload? = null,

	@Json(name="type")
	val type: String? = null
)