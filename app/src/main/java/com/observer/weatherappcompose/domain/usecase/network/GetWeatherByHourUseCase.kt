package com.observer.weatherappcompose.domain.usecase.network

import com.observer.weatherappcompose.domain.entity.WeatherModel
import com.observer.weatherappcompose.domain.repository.NetWorkRepositry
import javax.inject.Inject

class GetWeatherByHourUseCase @Inject constructor(
   private val repositry: NetWorkRepositry
) {

    suspend operator fun invoke(hours: String):List<WeatherModel>{
        return repositry.getWeatherByHour(hours)
    }
}
