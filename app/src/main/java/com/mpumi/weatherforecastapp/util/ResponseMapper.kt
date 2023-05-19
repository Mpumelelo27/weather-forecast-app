package com.mpumi.weatherforecastapp.util

import com.mpumi.weatherforecastapp.data.WeatherForecast
import com.mpumi.weatherforecastapp.data.WeatherForecastCurrent
import com.mpumi.weatherforecastapp.data.persistence.models.WeatherForecastCurrentStore
import com.mpumi.weatherforecastapp.data.persistence.models.WeatherForecastStore

object ResponseMapper {

    fun mapWeatherForecastResponse(response: WeatherForecast): List<WeatherForecastStore> {
        val storeItems: MutableList<WeatherForecastStore> = mutableListOf()
        response.list.map { theList ->
            theList.weather.map { weather ->
                storeItems.add(
                    WeatherForecastStore(
                        temp = theList.main?.temp ?: 0.0,
                        dtTxt = theList.dtTxt.orEmpty(),
                        main = weather.main.orEmpty(),
                        dt = theList.dt ?: 0L
                    )
                )
            }
        }
        return storeItems
    }

    fun mapWeatherForecastCurrentResponse(response: WeatherForecastCurrent): WeatherForecastCurrentStore {
        val main = response.main
        val weatherDescription = response.weather.firstOrNull()?.main.orEmpty()
        return WeatherForecastCurrentStore(
            temp = main?.temp ?: 0.0,
            tempMax = main?.tempMax ?: 0.0,
            tempMin = main?.tempMin ?: 0.0,
            main = weatherDescription
        )
    }
}