package com.observer.weatherappcompose.domain.usecase.allusecase.network

import com.observer.weatherappcompose.domain.usecase.network.GetDataRespUseCase
import com.observer.weatherappcompose.domain.usecase.network.GetWeatherByHourUseCase
import javax.inject.Inject

data class NetWorkAllUseCase @Inject constructor(
    val getDataUseCase: GetDataRespUseCase,
    val getWeatherByHourUseCase: GetWeatherByHourUseCase,
)
