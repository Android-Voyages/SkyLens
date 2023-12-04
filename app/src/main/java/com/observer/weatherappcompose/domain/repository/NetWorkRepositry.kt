package com.observer.weatherappcompose.domain.repository

import com.observer.weatherappcompose.domain.entity.WeatherModel
import kotlinx.coroutines.flow.MutableStateFlow

interface NetWorkRepositry {
    suspend fun getDataResp(
        city: String,
        daysList: MutableStateFlow<List<WeatherModel>>,
        currentDay: MutableStateFlow<WeatherModel>
    )

    suspend fun getWeatherByHour(hours: String): List<WeatherModel>
}
