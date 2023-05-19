package com.mpumi.weatherforecastapp.di

import android.app.Application
import androidx.room.Room
import com.mpumi.weatherforecastapp.BuildConfig
import com.mpumi.weatherforecastapp.api.WeatherForecastApi
import com.mpumi.weatherforecastapp.data.persistence.WeatherForecastDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Provides
    @Singleton //Made it singleton so that we can share the same instance of the retrofit in all places needed in the app
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(WeatherForecastApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideWeatherForecastApi(retrofit: Retrofit): WeatherForecastApi =
        retrofit.create(WeatherForecastApi::class.java)

    @Provides
    @Singleton
    fun provideWeatherForecastDatabase(app: Application): WeatherForecastDatabase =
        Room.databaseBuilder(app, WeatherForecastDatabase::class.java, "weather_forecast_db")
            .build()

}