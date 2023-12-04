package com.observer.weatherappcompose.presentation.viewModel


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.observer.weatherappcompose.domain.entity.WeatherModel
import com.observer.weatherappcompose.domain.usecase.allusecase.network.NetWorkAllUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val netWorkAllUseCase: NetWorkAllUseCase,
    private val scope: CoroutineScope
) : ViewModel() {


    private val _cityName = MutableStateFlow("")
    private val _daysList = MutableStateFlow(listOf<WeatherModel>())
    private val _dialogState = MutableStateFlow(false)
    private val _dialogText = MutableStateFlow("")
    private val _currentDay = MutableStateFlow(
        WeatherModel(
            "",
            "",
            "",
            "",
            "0.0",
            "0.0",
            "0.0",
            ""
        )
    )

    val cityName get() = _cityName.asStateFlow()
    val daysList get() = _daysList.asStateFlow()
    val dialogState get() = _dialogState.asStateFlow()
    val dialogText get() = _dialogText.asStateFlow()
    val currentDay get() = _currentDay.asStateFlow()

    fun updateCurrentDays(item: WeatherModel){
        Log.d("ViewModel", "Updating current day: $item")
        _currentDay.value = item
    }

    fun updateDialogState(item: Boolean){
        _dialogState.value = item
    }

    fun updateDialogState(item: String){
        _dialogText.value = item
    }
    fun updateCityName(newCityName: String) {
        _cityName.value = newCityName
    }

    fun getDataResp(
        city: String,
    ) {
        scope.launch {
            netWorkAllUseCase.getDataUseCase.invoke(city, _daysList, _currentDay)
        }
    }

    suspend fun getWeatherByHour(hours: String): List<WeatherModel> {
        return withContext(Dispatchers.IO) {
            netWorkAllUseCase.getWeatherByHourUseCase.invoke(hours)
        }
    }


}
