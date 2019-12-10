package com.example.qurbatask.main.LoadPlaces

import com.example.qurbatask.mviBase.MviAction
import com.example.qurbatask.mviBase.MviResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadPlacesAction (val jwt:String): MviAction<LoadPlacesResult>() {
    override suspend fun invoke(): Flow<LoadPlacesResult> {
        return flow {
            emit(LoadPlacesResult.Loading)
        }
    }

}

sealed class LoadPlacesResult : MviResult() {
    object Loading : LoadPlacesResult()

}