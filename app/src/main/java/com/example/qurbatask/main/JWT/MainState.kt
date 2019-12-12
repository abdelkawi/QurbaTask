package com.example.qurbatask.main.JWT

import com.example.qurbatask.mviBase.MviState

data class MainState(
    val isLoading: Boolean = true,
    val jwt: String = ""
) : MviState()