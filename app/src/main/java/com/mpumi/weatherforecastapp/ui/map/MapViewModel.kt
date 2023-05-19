package com.mpumi.weatherforecastapp.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "MapFragment, Not yet implemented"
    }
    val text: LiveData<String> = _text
}