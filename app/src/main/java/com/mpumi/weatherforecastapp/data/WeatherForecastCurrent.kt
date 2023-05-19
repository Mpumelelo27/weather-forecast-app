package com.mpumi.weatherforecastapp.data

import com.google.gson.annotations.SerializedName

data class WeatherForecastCurrent(
    @SerializedName("weather")
    val weather: List<Weather> = listOf(),
    @SerializedName("main")
    val main: Main? = null,
)