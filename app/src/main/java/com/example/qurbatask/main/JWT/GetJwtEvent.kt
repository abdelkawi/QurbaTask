package com.example.qurbatask.main.JWT

import com.example.qurbatask.mviBase.MviEvent
import com.example.qurbatask.network.genericEntities.ApiRequest

sealed class GetJwtEvent : MviEvent() {
    class Started(val authRequest: ApiRequest) : GetJwtEvent()
}

