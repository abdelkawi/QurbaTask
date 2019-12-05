package com.example.qurbatask.network.entities

import com.squareup.moshi.Json

data class Payload(

	@Json(name="role")
	val role: String? = null,

	@Json(name="jwt")
	val jwt: String? = null,

	@Json(name="_id")
	val id: String? = null
)