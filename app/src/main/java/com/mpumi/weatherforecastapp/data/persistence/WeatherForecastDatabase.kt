package com.mpumi.weatherforecastapp.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mpumi.weatherforecastapp.data.persistence.dao.WeatherForecastDao
import com.mpumi.weatherforecastapp.data.persistence.models.WeatherForecastCurrentStore
import com.mpumi.weatherforecastapp.data.persistence.models.WeatherForecastStore

@Database(
    entities = [WeatherForecastStore::class, WeatherForecastCurrentStore::class],
    version = 1,
    exportSchema = true
)
abstract class WeatherForecastDatabase : RoomDatabase() {

    abstract fun weatherForecastDao(): WeatherForecastDao
}