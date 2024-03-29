package com.example.qurbatask

import android.annotation.SuppressLint
import com.example.qurbatask.network.genericEntities.ApiResponse
import com.example.qurbatask.network.Failure
import timber.log.Timber
import java.io.IOException
import com.squareup.moshi.JsonDataException
import retrofit2.HttpException
import com.example.qurbatask.network.Result

@SuppressLint("TimberArgCount")
suspend fun <T> safeApiCall(
    call: suspend () -> ApiResponse<T>
): Result<T> {
    return try {
        val result = call()
        if (result.type == "SUCCESS") {
            val data = result.payload
            return if (data != null) {
                Result.Success(data)
            } else {
                Result.Error(Failure.NoDataError())
            }
        }
        Timber.i("Error", "safe call api try error")
        return Result.Error(Failure.ServerError("ServerError"))
    } catch (e: Exception) {
        e.printStackTrace()
        when (e) {
            is HttpException -> {
                when (e.code()) {
                    in 300 until 400 -> {
                        Result.Error(Failure.NetworkConnection("غير مصرح لك."))
                    }
                    in 400 until 500 -> {
                        Result.Error(Failure.NetworkConnection("برجاء التاكد من البيانات."))
                    }
                    in 500 until 600 -> {
                        Result.Error(Failure.NetworkConnection("حدث خطا في السيرفر. برجاء المحاولة بعد قليل."))
                    }
                    else -> {
                        Result.Error(Failure.NetworkConnection("حدث خطا ما. برجاء المحاولة مرة اخري."))
                    }
                }
            }
            is IOException -> {
                Result.Error(Failure.NetworkConnection("لا يوجد اتصال برجاء التاكد من اتصالك بالانترنت."))
            }
            is JsonDataException -> {
                Result.Error(Failure.ServerError("Data does not match"))
            }
            else -> {
                Result.Error(Failure.NetworkConnection("حدث خطا ما. برجاء المحاولة مرة اخري."))
            }
        }
    }
}