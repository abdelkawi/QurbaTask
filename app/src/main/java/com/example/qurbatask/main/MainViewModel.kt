package com.example.qurbatask.main

import BaseViewModel
import com.example.qurbatask.mviBase.MviAction
import com.example.qurbatask.mviBase.MviEffect
import com.example.qurbatask.mviBase.MviResult
import com.example.qurbatask.network.RemoteDataSource

class MainViewModel(val remoteDataSource: RemoteDataSource) :
    BaseViewModel<MainEvent, MainState>(initialState = MainState()) {

    override suspend fun eventToAction(uiEvent: MainEvent): MviAction<MviResult> {
        return when (uiEvent) {
            is MainEvent.Started -> MainAction.Started(
                uiEvent.authRequest,
                remoteDataSource
            )
        }
    }

    override suspend fun resultToUiModel(state: MainState, result: MviResult): MainState {
        return when (result) {
            is MainResult.LoadingResult -> currentState.copy(isLoading = true, message = "loading")
            is MainResult.SuccessResult -> currentState.copy(
                isLoading = false,
                message = result.data.payload?.jwt
            )
            is MainResult.ErrorResult -> currentState.copy(
                isLoading = false,
                message = result.errorMsg
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