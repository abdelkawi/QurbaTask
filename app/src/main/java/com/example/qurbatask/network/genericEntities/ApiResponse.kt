package com.example.qurbatask.network.genericEntities


data class ApiResponse<T>(
    var type:String?,
    var payload: T?

)