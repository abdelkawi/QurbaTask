package com.example.qurbatask.main

import com.example.qurbatask.mviBase.MviEvent
import com.example.qurbatask.network.entities.AuthRequest

sealed class MainEvent : MviEvent() {
    class Started(val authRequest: AuthRequest) : MainEvent()
}

