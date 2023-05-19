package com.mpumi.weatherforecastapp.api

import com.mpumi.weatherforecastapp.data.WeatherForecast
import com.mpumi.weatherforecastapp.data.WeatherForecastCurrent
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherForecastApi {
    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }

    @GET("forecast")
    suspend fun getWeatherForecast(
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("appid") apiId: String,
        @Query("units") units: String = "metric"
    ): WeatherForecast

    @GET("weather")
    suspend fun getWeatherForecastCurrent(
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("appid") apiId: String,
        @Query("units") units: String = "metric"
    ): WeatherForecastCurrent
}