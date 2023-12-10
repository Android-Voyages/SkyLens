package com.observer.weatherappcompose.presentation.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.LocationServices
import com.observer.weatherappcompose.presentation.ui.theme.WeatherAppComposeTheme
import com.observer.weatherappcompose.R
import com.observer.weatherappcompose.utills.Constant.CITY_NAME_PUT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import java.io.IOException
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppComposeTheme {
                SplashScreen()
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun SplashScreen() {
        val alpha = remember {
            Animatable(0f)
        }

        LaunchedEffect(key1 = true, block = {
            alpha.animateTo(1f, animationSpec = tween(1500))
            lifecycleScope.launch {
                val cityName = getCityUser()
                delay(5000)
                if (!cityName.isNullOrEmpty()) {
                    navigateToMain(cityName)
                } else {
                    showCityInputDialog()
                }
            }
        })

        Image(
            painter = painterResource(id = R.drawable.background_splash),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .alpha(alpha.value),
            contentScale = ContentScale.FillBounds
        )

    }

    private fun showCityInputDialog() {
        val inputField = EditText(this@SplashActivity)
        inputField.inputType = InputType.TYPE_CLASS_TEXT
        inputField.hint = "Enter City Name"

        AlertDialog.Builder(this)
            .setTitle("Enter Your City")
            .setView(inputField)
            .setPositiveButton("Submit") { dialog, which ->
                val cityName = inputField.text.toString()
                if (cityName.isNotEmpty()) {
                    navigateToMain(cityName)
                }
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    private fun navigateToMain(cityName: String) {
        val intent = Intent(this@SplashActivity, MainActivity::class.java).apply {
            putExtra(CITY_NAME_PUT, cityName)
        }
        startActivity(intent)
        finish()
    }
    private suspend fun getCityUser(): String? = withTimeoutOrNull(5000) { // 5 секунд таймаут
        suspendCancellableCoroutine { continuation ->
            val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this@SplashActivity)
            if (ActivityCompat.checkSelfPermission(this@SplashActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this@SplashActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) {
                locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
                continuation.resume(null)
            } else {
                fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
                    location?.let {
                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                val geocoder = Geocoder(this@SplashActivity, Locale.getDefault())
                                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                                val cityName = addresses?.get(0)?.locality
                                if (cityName != null) {
                                    continuation.resume(cityName)
                                } else {
                                    showCityInputDialog()
                                }
                            } catch (e: IOException) {
                                showCityInputDialog()
                            }
                        }
                    } ?:  showCityInputDialog()
                }.addOnFailureListener { e ->
                    showCityInputDialog()
                }
            }
        }
    }
}
