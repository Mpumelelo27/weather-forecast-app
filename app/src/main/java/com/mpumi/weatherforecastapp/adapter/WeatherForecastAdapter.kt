package com.mpumi.weatherforecastapp.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.mpumi.weatherforecastapp.R
import com.mpumi.weatherforecastapp.data.persistence.models.WeatherForecastStore
import com.mpumi.weatherforecastapp.databinding.WeatherForecastItemsBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class WeatherForecastAdapter(private var weatherForecast: List<WeatherForecastStore> = listOf()) :
    RecyclerView.Adapter<WeatherForecastAdapter.WeatherForecastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherForecastViewHolder {
        return WeatherForecastViewHolder(
            WeatherForecastItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = weatherForecast.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: WeatherForecastViewHolder, position: Int) {
        holder.bind(weatherForecast[position])
    }

    inner class WeatherForecastViewHolder(private val binding: WeatherForecastItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(data: WeatherForecastStore) {

            val firstApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDate.parse(data.dtTxt, firstApiFormat)
            binding.forecastDay.text = date.dayOfWeek.toString()
            setIconAndTemp(
                binding.forecastTemp,
                binding.forecastIcon,
                data.main,
                "${data.temp.toInt()}°"
            )
        }

        private fun setIconAndTemp(
            textView: TextView,
            imageView: ImageView,
            main: String,
            temp: String,
        ) {
            val icon = when (main) {
                "Rain" -> R.drawable.rain
                "Clouds" -> R.drawable.partlysunny
                else -> R.drawable.clear
            }
            imageView.setImageResource(icon)
            textView.text = temp
        }

    }
}