package com.mpumi.weatherforecastapp.data.persistence.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mpumi.weatherforecastapp.data.persistence.models.WeatherForecastCurrentStore.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class WeatherForecastCurrentStore(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val temp: Double,
    val tempMin: Double,
    val tempMax: Double,
    val main: String,
) {
    companion object {
        const val TABLE_NAME = "weatherForecast"
    }
}