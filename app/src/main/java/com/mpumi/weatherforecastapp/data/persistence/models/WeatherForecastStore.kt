package com.mpumi.weatherforecastapp.data.persistence.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mpumi.weatherforecastapp.data.persistence.models.WeatherForecastStore.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class WeatherForecastStore(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val temp: Double,
    val dtTxt: String,
    val dt: Long,
    val main: String
) {
    companion object {
        const val TABLE_NAME = "weatherForecastCurrent"
    }
}