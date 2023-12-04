package com.observer.weatherappcompose.domain.usecase.network

import com.observer.weatherappcompose.domain.repository.NetWorkRepositry
import javax.inject.Inject

class GetDataUseCase @Inject constructor(
    private val repositry: NetWorkRepositry
) {
    suspend fun invoke(city:String){
         repositry.getData(city)
    }
}
