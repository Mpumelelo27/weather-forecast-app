package com.mpumi.weatherforecastapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherForecastApplication : Application() //It will kick off our dependency injection process for our whole app