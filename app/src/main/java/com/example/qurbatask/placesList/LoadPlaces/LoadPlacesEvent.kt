package com.example.qurbatask.placesList.LoadPlaces

import com.example.qurbatask.mviBase.MviEvent
import com.example.qurbatask.network.genericEntities.ApiRequest

sealed class LoadPlacesEvent : MviEvent() {
    class Started(val jwt:String) : LoadPlacesEvent()
}

