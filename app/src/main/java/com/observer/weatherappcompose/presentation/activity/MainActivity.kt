package com.observer.weatherappcompose.presentation.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.observer.weatherappcompose.R
import com.observer.weatherappcompose.presentation.ui.screen.alertDialogSearch
import com.observer.weatherappcompose.presentation.ui.screen.mainCard
import com.observer.weatherappcompose.presentation.ui.screen.tabLayout
import com.observer.weatherappcompose.presentation.ui.theme.WeatherAppComposeTheme
import com.observer.weatherappcompose.presentation.viewModel.MainViewModel
import com.observer.weatherappcompose.utills.Constant.CITY_NAME_PUT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cityName = intent.getStringExtra(CITY_NAME_PUT)
        cityName?.let {
            mainViewModel.updateCityName(it)
            mainViewModel.getDataResp(it)
        }
        setContent {
            WeatherAppComposeTheme {

                val cityName = mainViewModel.cityName.collectAsState()
                val currentDay = mainViewModel.currentDay.collectAsState()
                val dialogState = mainViewModel.dialogState.collectAsState()
                Log.d("afsafaffafafafa", cityName.value)
                if (dialogState.value) {
                    alertDialogSearch(
                        mainViewModel,
                        onSubmit = {
                            val bytes = it.toByteArray(Charsets.UTF_8)
                            val encodedString = String(bytes, Charsets.UTF_8)
                            mainViewModel.getDataResp(encodedString)
                        },
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.background_main),
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
}


