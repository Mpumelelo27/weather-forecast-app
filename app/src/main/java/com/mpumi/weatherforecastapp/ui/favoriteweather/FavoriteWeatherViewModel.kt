package com.mpumi.weatherforecastapp.ui.favoriteweather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FavoriteWeatherViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Favorite Fragment Not yet implemented"
    }
    val text: LiveData<String> = _text
}