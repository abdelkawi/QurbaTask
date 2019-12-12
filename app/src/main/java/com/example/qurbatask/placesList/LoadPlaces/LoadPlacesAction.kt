package com.example.qurbatask.placesList.LoadPlaces

import com.example.qurbatask.mviBase.MviAction
import com.example.qurbatask.mviBase.MviResult
import com.example.qurbatask.network.RemoteDataSource
import com.example.qurbatask.network.Result
import com.example.qurbatask.network.request.NearbyRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadPlacesAction(

    val jwt: String, val remoteDataSource: RemoteDataSource, val nearbyRequest: NearbyRequest
) : MviAction<LoadPlacesResult>() {

    override suspend fun invoke(): Flow<LoadPlacesResult> {
        return flow {
            emit(LoadPlacesResult.Loading)
            val res = remoteDataSource.getNearby(jwt, nearbyRequest)
            when (res) {
                is Result.Success -> emit(LoadPlacesResult.Success("sucess"))
                is Result.Error -> emit(LoadPlacesResult.Error(res.error.toString()))
            }
        }
    }

}

sealed class LoadPlacesResult : MviResult() {
    object Loading : LoadPlacesResult()
    data class Error(val error: String) : LoadPlacesResult()
    data class Success(val success: String) : LoadPlacesResult()

}