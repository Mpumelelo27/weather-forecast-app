package com.mpumi.weatherforecastapp.data.repository

import com.mpumi.weatherforecastapp.api.WeatherForecastApi
import com.mpumi.weatherforecastapp.data.persistence.WeatherForecastDatabase
import com.mpumi.weatherforecastapp.util.ResponseMapper
import com.mpumi.weatherforecastapp.util.networkBoundResource
import javax.inject.Inject

class WeatherForeCastRepository @Inject constructor(
    private val api: WeatherForecastApi,
    db: WeatherForecastDatabase
) {
    private val dao = db.weatherForecastDao()

    fun getWeatherForecast(lat: Double, long: Double, apiId: String) = networkBoundResource(
        query = {
            dao.getAllWeatherForecast()
        },
        fetch = {
            api.getWeatherForecast(lat = lat, long = long, apiId = apiId)
        },
        saveFetchResult = {
            val store = ResponseMapper.mapWeatherForecastResponse(it)
            dao.updateWeatherForecast(store)
        }
    )

    fun getWeatherForecastCurrent(lat: Double, long: Double, apiId: String) = networkBoundResource(
        query = {
            dao.getAllWeatherForecastCurrent()
        },
        fetch = {
            api.getWeatherForecastCurrent(lat, long, apiId)
        },
        saveFetchResult = {
            val store = ResponseMapper.mapWeatherForecastCurrentResponse(it)
            dao.updateWeatherForecastCurrent(store)
        }
    )
}