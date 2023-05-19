package com.mpumi.weatherforecastapp.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.navigation.NavigationView
import com.mpumi.weatherforecastapp.Constants
import com.mpumi.weatherforecastapp.R
import com.mpumi.weatherforecastapp.databinding.ActivityWeatherForecastMainBinding
import com.mpumi.weatherforecastapp.ui.home.WeatherForecastViewModel
import com.mpumi.weatherforecastapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WeatherForecastMainActivity : AppCompatActivity() {
    private val viewModel: WeatherForecastViewModel by viewModels()

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityWeatherForecastMainBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWeatherForecastMainBinding.inflate(layoutInflater)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarNavigation.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_navigation)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_weather_forecast, R.id.nav_favorite_weather, R.id.nav_map
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        getLocation()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.navigation, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_navigation)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun observeData(lat: Double, lon: Double) {
        viewModel.weatherForecastCurrent(
            lat,
            lon,
            Constants.API_KEY
        ).observe(this) { resource ->
            when (resource) {
                is Resource.Success -> resource.data?.let {
                    setUpHeaderView(it)
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
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                100
            )
            return
        }

        val location = fusedLocationProviderClient.lastLocation
        location.addOnSuccessListener {
            if (it != null) {
                observeData(it.latitude, it.longitude)
            }
        }
    }

    //Set the nav headerLayout background according to the returned current weather data
    private fun setUpHeaderView(data: WeatherForecastViewModel.CurrentWeatherForecastViewData) {
        val header = binding.navView.getHeaderView(0)
        val sideNavLayout = header.findViewById<View>(R.id.header_layout) as LinearLayout
        val icon = when (data.currentTempDesc) {
            "Rainy" -> R.drawable.sea_rainy
            "Cloudy" -> R.drawable.sea_cloudy
            else -> R.drawable.sea_rainy
        }
        sideNavLayout.setBackgroundResource(icon)
    }
}