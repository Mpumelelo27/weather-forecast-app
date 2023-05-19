package com.mpumi.weatherforecastapp.data.persistence.dao

import androidx.room.*
import com.mpumi.weatherforecastapp.data.persistence.models.WeatherForecastCurrentStore
import com.mpumi.weatherforecastapp.data.persistence.models.WeatherForecastStore
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherForecastDao {

    @Transaction
    suspend fun updateWeatherForecast(weatherForecastStore: List<WeatherForecastStore>) {
        deleteWeatherForecast()
        insertWeatherForecast(weatherForecastStore)
    }

    @Query("SELECT * FROM ${WeatherForecastStore.TABLE_NAME}")
    fun getAllWeatherForecast(): Flow<List<WeatherForecastStore>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherForecast(weatherForecastStore: List<WeatherForecastStore>)

    @Query("DELETE FROM ${WeatherForecastStore.TABLE_NAME}")
    suspend fun deleteWeatherForecast()

    @Transaction
    suspend fun updateWeatherForecastCurrent(weatherForecastCurrentStore: WeatherForecastCurrentStore) {
        deleteWeatherForecastCurrent()
        insertWeatherForecastCurrent(weatherForecastCurrentStore)
    }

    @Query("SELECT * FROM ${WeatherForecastCurrentStore.TABLE_NAME}")
    fun getAllWeatherForecastCurrent(): Flow<WeatherForecastCurrentStore>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherForecastCurrent(weatherForecastCurrentStore: WeatherForecastCurrentStore)

    @Query("DELETE FROM ${WeatherForecastCurrentStore.TABLE_NAME}")
    suspend fun deleteWeatherForecastCurrent()
}