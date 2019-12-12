package com.example.qurbatask.main

import BaseViewModel
import com.example.qurbatask.main.JWT.*
import com.example.qurbatask.placesList.LoadPlaces.LoadPlacesAction
import com.example.qurbatask.placesList.LoadPlaces.LoadPlacesEvent
import com.example.qurbatask.mviBase.MviAction
import com.example.qurbatask.mviBase.MviEffect
import com.example.qurbatask.mviBase.MviEvent
import com.example.qurbatask.mviBase.MviResult
import com.example.qurbatask.network.RemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class MainViewModel(val remoteDataSource: RemoteDataSource) :
    BaseViewModel<MviEvent, MainState>(
        MainState()
    ) {

    override suspend fun eventToAction(uiEvent: MviEvent): MviAction<MviResult> {
        return when (uiEvent) {

            is GetJwtEvent.Started -> GetJwtAction(
                uiEvent.authRequest,
                remoteDataSource
            )
            else -> throw IllegalArgumentException("Not Supported Event")
        }
    }

    override suspend fun resultToUiModel(state: MainState, result: MviResult): MainState {
        return when (result) {
            is GetJWTResult.LoadingResult -> currentState.copy(
                isLoading = true
            )
            is GetJWTResult.SuccessResult -> currentState.copy(isLoading = false, jwt = result.jwt)
            is GetJWTResult.ErrorResult -> currentState.copy(
                isLoading = false
            )
            else -> throw IllegalArgumentException("Not Supported Result")
        }
    }

    override suspend fun resultToAction(result: MviResult): MviAction<MviResult>? {
        return null
    }

    override suspend fun resultToEffect(result: MviResult): MviEffect? {
        return null
    }


}