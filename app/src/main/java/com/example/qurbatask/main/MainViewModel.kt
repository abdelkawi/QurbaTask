package com.example.qurbatask.main

import BaseViewModel
import com.example.qurbatask.main.JWT.*
import com.example.qurbatask.main.LoadPlaces.LoadPlacesAction
import com.example.qurbatask.main.LoadPlaces.LoadPlacesResult
import com.example.qurbatask.mviBase.MviAction
import com.example.qurbatask.mviBase.MviEffect
import com.example.qurbatask.mviBase.MviResult
import com.example.qurbatask.network.RemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class MainViewModel(val remoteDataSource: RemoteDataSource) :
    BaseViewModel<GetJwtEvent, MainState>(MainState()) {

    override suspend fun eventToAction(uiEvent: GetJwtEvent): MviAction<MviResult> {
        return when (uiEvent) {
            is GetJwtEvent.Started -> GetJwtAction(
                uiEvent.authRequest,
                remoteDataSource
            )
        }
    }

    override suspend fun resultToUiModel(state: MainState, result: MviResult): MainState {
        return when (result) {
            is GetJWTResult.LoadingResult -> currentState.copy(
                isLoading = true,
                message = "loading"
            )
            is GetJWTResult.ErrorResult -> currentState.copy(
                isLoading = false,
                message = result.errorMsg
            )
            is LoadPlacesResult.Loading -> currentState.copy(message = "hi from action")
            else -> throw IllegalArgumentException("Not Supported Result")
        }
    }

    override suspend fun resultToAction(result: MviResult): MviAction<MviResult>? {

        return when (result) {
            is GetJWTResult.SuccessResult -> LoadPlacesAction(result.jwt)
            else -> throw IllegalArgumentException("Not Supported Result")
        }
    }

    override suspend fun resultToEffect(result: MviResult): MviEffect? {
        return null
    }


}