package com.mpumi.weatherforecastapp.data

import com.google.gson.annotations.SerializedName

data class WeatherForecast(
    @SerializedName("list")
    val list: List<TheList> = listOf()
)

data class Weather(
    @SerializedName("main")
    val main: String? = null,
    @SerializedName("description")
    val description: String? = null,
)

data class Main(
    @SerializedName("temp")
    val temp: Double? = null,
    @SerializedName("temp_min")
    val tempMin: Double? = null,
    @SerializedName("temp_max")
    val tempMax: Double? = null,
    @SerializedName("temp_kf")
    val tempKf: Double? = null
)

data class TheList(
    @SerializedName("main")
    val main: Main? = null,
    @SerializedName("weather")
    val weather: List<Weather> = listOf(),
    @SerializedName("dt_txt")
    val dtTxt: String? = null,
    @SerializedName("dt")
    val dt: Long? = null
)