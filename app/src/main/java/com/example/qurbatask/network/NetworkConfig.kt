package com.example.qurbatask.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

const val REQUIRE_AUTHENTICATION_HEADER = "Require-Authentication"
const val BASE_URL = "https://backend-user-alb.qurba-dev.com/"
const val AUTH_END_POINT = "auth/login-guest"
const val GET_NEARBY_PLACES ="places/places/nearby"

object NetworkConfig {
    private fun loggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        return logging
    }

    private fun buildClient(isDebug: Boolean): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.callTimeout(1, TimeUnit.MINUTES)
        client.connectTimeout(1, TimeUnit.MINUTES)
        client.readTimeout(1, TimeUnit.MINUTES)
        client.writeTimeout(1, TimeUnit.MINUTES)
        if (isDebug) {
            client.addInterceptor(loggingInterceptor())
            client.addInterceptor(authInterceptor())
            client.addNetworkInterceptor(StethoInterceptor())

        }
        return client.build()
    }


    private fun authInterceptor(): Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                var request: Request = chain.request()
                request = request.newBuilder().addHeader("Content-Type", "application/json")
                    .addHeader("version", "3.0")
                    .addHeader("language","en")
                    .build()
                return chain.proceed(request)
            }
        }
    }

    private fun retrofitMoshiConverter(): Converter.Factory {
        val moshi = Moshi.Builder().build()
        return MoshiConverterFactory.create(moshi)
    }

    private fun getRetrofit(baseUrl: String, isDebug: Boolean = false): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(retrofitMoshiConverter())
            .client(buildClient(isDebug))
            .build()
    }

    fun <T> createService(baseUrl: String, isDebug: Boolean = false, service: Class<T>): T {
        return getRetrofit(baseUrl, isDebug).create(service)
    }


}