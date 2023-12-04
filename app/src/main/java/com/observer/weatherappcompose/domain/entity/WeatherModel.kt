package com.observer.weatherappcompose.domain.entity

data class WeatherModel(
    val city: String,
    val time: String,
    val condition: String,
    val icon: String,
    val maxTemp:String,
    val minTemp:String,
    val currentTemp: String,
    val hours: String
)
