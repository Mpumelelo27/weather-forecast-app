package com.mpumi.weatherforecastapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mpumi.weatherforecastapp.data.persistence.models.WeatherForecastStore
import com.mpumi.weatherforecastapp.data.repository.WeatherForeCastRepository
import com.mpumi.weatherforecastapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class WeatherForecastViewModel @Inject constructor(private val repository: WeatherForeCastRepository) :
    ViewModel() {

    fun weatherForecast(
        lat: Double,
        lon: Double,
        apiId: String
    ): LiveData<Resource<List<WeatherForecastStore>>> {
        return repository.getWeatherForecast(lat, lon, apiId).map {
            it
        }.asLiveData()
    }

    fun weatherForecastCurrent(
        lat: Double,
        lon: Double,
        apiId: String
    ): LiveData<Resource<CurrentWeatherForecastViewData>> {
        return repository.getWeatherForecastCurrent(lat, lon, apiId).map {
            val data = it.data
            val tempDesc = when (data?.main) {
                "Rain" -> "Rainy"
                "Clouds" -> "Cloudy"
                else -> "Sunny"
            }
            Resource.Success(
                CurrentWeatherForecastViewData(
                    data?.temp,
                    data?.tempMin,
                    data?.tempMax,
                    tempDesc
                )
            )
        }.asLiveData()
    }

    data class CurrentWeatherForecastViewData(
        val currentTemp: Double?,
        val minTemp: Double?,
        val maxTemp: Double?,
        val currentTempDesc: String?
    )
}