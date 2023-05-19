package com.mpumi.weatherforecastapp.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mpumi.weatherforecastapp.Constants
import com.mpumi.weatherforecastapp.R
import com.mpumi.weatherforecastapp.adapter.WeatherForecastAdapter
import com.mpumi.weatherforecastapp.data.persistence.models.WeatherForecastStore
import com.mpumi.weatherforecastapp.databinding.FragmentWeatherForecastBinding
import com.mpumi.weatherforecastapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherForecastFragment : Fragment() {

    private val viewModel: WeatherForecastViewModel by viewModels()
    private var _binding: FragmentWeatherForecastBinding? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherForecastBinding.inflate(inflater, container, false)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLocation()
    }

    private fun observeCurrentWeatherForecastData(lat: Double, lon: Double) {
        viewModel.weatherForecastCurrent(
            lat, lon, Constants.API_KEY
        ).observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> resource.data?.let {
                    setUpCurrentWeatherForecastData(it)
                }
                is Resource.Error -> {
                    //Handle error state
                }
                is Resource.Loading -> {
                    //Handle loading state
                }
            }
        }
    }

    private fun observeWeatherForecastData(lat: Double, lon: Double) {
        viewModel.weatherForecast(
            lat, lon, Constants.API_KEY
        ).observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> resource.data?.let {
                    setUpWeatherForecastAdapter(it)
                }
                is Resource.Error -> {
                    //Handle error state
                }
                is Resource.Loading -> {
                    //Handle loading state
                }
            }
        }
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                100
            )
            return
        }

        val location = fusedLocationProviderClient.lastLocation
        location.addOnSuccessListener {
            if (it != null) {
                observeWeatherForecastData(it.latitude, it.longitude)
                observeCurrentWeatherForecastData(it.latitude, it.longitude)
            }
        }
    }

    private fun setUpWeatherForecastAdapter(store: List<WeatherForecastStore>) {
        binding.weatherForecastList.adapter = WeatherForecastAdapter(store)
    }

    private fun setUpCurrentWeatherForecastData(data: WeatherForecastViewModel.CurrentWeatherForecastViewData) {
        binding.currentTempInLinear.text = "${data.currentTemp?.toInt()}°"
        binding.currentTempDescInLinear.text = data.currentTempDesc
        binding.minTemp.text = "${data.minTemp?.toInt()}°"
        binding.currentTemp.text = "${data.currentTemp?.toInt()}°"
        binding.maxTemp.text = "${data.maxTemp?.toInt()}°"

        val icon = when (data.currentTempDesc) {
            "Rainy" -> R.drawable.sea_rainy
            "Cloudy" -> R.drawable.sea_cloudy
            else -> R.drawable.sea_rainy
        }
        binding.currentTempContentLayout.setBackgroundResource(icon)

        val contentBackgroundColor = when (data.currentTempDesc) {
            "Rainy" -> "#57575D"
            "Cloudy" -> "#54717A"
            else -> "#47AB2F"
        }
        binding.weatherForecastContentLayout.setBackgroundColor(
            Color.parseColor(
                contentBackgroundColor
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}