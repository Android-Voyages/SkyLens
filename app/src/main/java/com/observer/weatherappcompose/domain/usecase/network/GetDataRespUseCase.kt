package com.observer.weatherappcompose.domain.usecase.network

import com.observer.weatherappcompose.domain.entity.WeatherModel
import com.observer.weatherappcompose.domain.repository.NetWorkRepositry
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class GetDataRespUseCase @Inject constructor(
    private val repositry: NetWorkRepositry
) {
    suspend operator fun invoke(
        city: String, daysList: MutableStateFlow<List<WeatherModel>>,
        currentDay: MutableStateFlow<WeatherModel>
    ){
         repositry.getDataResp(city,daysList,currentDay)
    }
}
