package com.observer.weatherappcompose.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.observer.weatherappcompose.R
import com.observer.weatherappcompose.presentation.ui.screen.alertDialogSearch
import com.observer.weatherappcompose.presentation.ui.screen.mainCard
import com.observer.weatherappcompose.presentation.ui.screen.tabLayout
import com.observer.weatherappcompose.presentation.ui.theme.WeatherAppComposeTheme
import com.observer.weatherappcompose.presentation.viewModel.MainViewModel
import com.observer.weatherappcompose.utills.Constant.GEO_CODER_RES
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
            }

            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
            }

            else -> {
                // No location access granted.
            }
        }
    }


    init {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WeatherAppComposeTheme {

                val cityName = mainViewModel.cityName.collectAsState()
                val currentDay = mainViewModel.currentDay.collectAsState()
                val dialogState = mainViewModel.dialogState.collectAsState()
                if (dialogState.value) {
                    alertDialogSearch(
                        mainViewModel,
                        onSubmit = {
                            mainViewModel.getDataResp(it)
                        }
                    )
                }
                getCityUser()
                LaunchedEffect(key1 = true) {
                    mainViewModel.getDataResp(cityName.value)
                }
                Image(
                    painter = painterResource(id = R.drawable.background_sunny),
                    contentDescription = "im1",
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.5f),
                    contentScale = ContentScale.FillBounds
                )
                Column {
                    mainCard(
                        item = currentDay.value,
                        onClickSyns = {
                            mainViewModel.getDataResp(cityName.value)
                        },
                        onClickSearch = {
                            mainViewModel.updateDialogState(true)
                        }
                    )


                    tabLayout(mainViewModel.daysList, currentDay.value, mainViewModel)
                }
            }
        }
    }


    private fun getCityUser() {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        val scope = CoroutineScope(Dispatchers.Main)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        } else {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    val geocoder = Geocoder(this, Locale.getDefault())
                    try {
                        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                        val cityName = addresses?.get(0)?.locality
                        if (cityName != null) {
                            Log.d(GEO_CODER_RES, "City Name: $cityName")
                            scope.launch {
                                mainViewModel.updateCityName(cityName)
                            }
                        } else {
                            Log.d(GEO_CODER_RES, "City Name not found")
                        }
                    } catch (e: IOException) {
                        Log.e(GEO_CODER_RES, "Error in geocoding", e)
                        scope.launch {
                            mainViewModel.updateCityName(e.message.toString())
                        }
                    }

                }
            }.addOnFailureListener {
            }
        }
    }

}


