package com.example.qurbatask.main

import com.example.qurbatask.mviBase.MviState

data class MainState(val isLoading: Boolean = true, val message: String? = null) : MviState()