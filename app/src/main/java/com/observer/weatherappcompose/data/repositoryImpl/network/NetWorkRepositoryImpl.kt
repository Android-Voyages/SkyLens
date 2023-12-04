package com.observer.weatherappcompose.data.repositoryImpl.network

import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.observer.weatherappcompose.domain.entity.WeatherModel
import com.observer.weatherappcompose.domain.repository.NetWorkRepositry
import com.observer.weatherappcompose.utills.Constant
import com.observer.weatherappcompose.utills.Constant.CELSIUS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetWorkRepositoryImpl @Inject constructor(
    private val queue: RequestQueue,
) : NetWorkRepositry {
    override suspend fun getDataResp(
        city: String,
        daysList: MutableStateFlow<List<WeatherModel>>,
        currentDay: MutableStateFlow<WeatherModel>
    ) {
        val url = "https://api.weatherapi.com/v1/forecast.json" +
            "?key=${Constant.API_KEY}" +
            "&q=$city" +
            "&days=7" +
            "&aqi=no&alerts=no\n"

        val sRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                CoroutineScope(Dispatchers.IO).launch {
                    val list = getWeatherbyDays(response)
                    withContext(Dispatchers.Main) {
                        if (list.isNotEmpty()) {
                            currentDay.value = list[0]
                            daysList.value = list
                        } else {
                            Log.d(Constant.LOG_RESPONSE, "Weather list is empty")
                            delay(2000)
                        }
                    }
                }
            },
            { error ->
                Log.d(Constant.LOG_VOLLEY_ERROR, "VolleyError: $error")
            }
        )
        queue.add(sRequest)
    }

    private fun getWeatherbyDays(response: String): List<WeatherModel> {
        if (response.isEmpty()) {
            Log.d(Constant.LOG_RESPONSE, "Response: empty")
            return listOf()
        } else {
            try {
                val list = arrayListOf<WeatherModel>()
                val mainObject = JSONObject(response)
                val city = mainObject.getJSONObject("location").getString("name")
                val days = mainObject.getJSONObject("forecast").getJSONArray("forecastday")
                val current = mainObject.getJSONObject("current")
                for (i in 0 until days.length()) {
                    val item = days.getJSONObject(i)
                    val condition = item.getJSONObject("day").getJSONObject("condition")
                    val day = item.getJSONObject("day")
                    if (city.isNotEmpty() && item.has("date")) {
                        list.add(
                            WeatherModel(
                                city,
                                item.getString("date"),
                                condition.getString("text"),
                                condition.getString("icon"),
                                day.getString("maxtemp_c"),
                                day.getString("mintemp_c"),
                                "",
                                item.getJSONArray("hour").toString()
                            )
                        )
                    }
                }
                list[0] = list[0].copy(
                    time = current.getString("last_updated"),
                    currentTemp = current.getString("temp_c")
                )
                return list
            } catch (e: JSONException) {
                Log.e(Constant.LOG_RESPONSE, "JSON Parsing error: ${e.message}")
                return listOf()
            }
        }
    }

    override suspend fun getWeatherByHour(hours: String): List<WeatherModel> {
        if(hours.isEmpty()){
            Log.e(Constant.LOG_RESPONSE, "hours empty")
            return listOf()
        }else{
            val hoursArray = JSONArray(hours)
            val list = ArrayList<WeatherModel>()

            for (i in 0 until hoursArray.length()){
                val item = hoursArray[i] as JSONObject
                list.add(
                    WeatherModel(
                        "",
                            item.getString("time"),
                            item.getJSONObject("condition").getString("text"),
                            item.getJSONObject("condition").getString("icon"),
                            "",
                        "",
                        item.getString("temp_c").toFloat().toInt().toString() + CELSIUS,
                        ""
                    )
                )
            }
            return list
        }
    }

}
