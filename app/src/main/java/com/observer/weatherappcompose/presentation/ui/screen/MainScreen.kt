package com.observer.weatherappcompose.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.pagerTabIndicatorOffset
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.observer.weatherappcompose.R
import com.observer.weatherappcompose.domain.entity.WeatherModel
import com.observer.weatherappcompose.presentation.ui.theme.BlueLight
import com.observer.weatherappcompose.presentation.viewModel.MainViewModel
import com.observer.weatherappcompose.utills.Constant.CELSIUS
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


@Composable
fun mainCard(item: WeatherModel, onClickSyns: () -> Unit,onClickSearch: () -> Unit) {

    Column(
        modifier = Modifier
            .padding(5.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(0.dp, RoundedCornerShape(10.dp)),
            backgroundColor = BlueLight
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                        text = item.time,
                        style = TextStyle(fontSize = 15.sp),
                        color = Color.White
                    )

                    AsyncImage(
                        model = "https:${item.icon}",
                        contentDescription = "im2",
                        modifier = Modifier
                            .size(35.dp)
                            .padding(top = 3.dp, end = 8.dp)
                            .size(35.dp)
                    )
                }

                Text(
                    text = item.city,
                    style = TextStyle(fontSize = 24.sp),
                    color = Color.White
                )

                Text(
                    text = if (item.currentTemp.isNotEmpty()) {
                        item.currentTemp.toFloat().toInt().toString() + CELSIUS
                    } else {
                        "${
                            item.maxTemp.toFloat().toInt().toString() + CELSIUS
                        }/${item.minTemp.toFloat().toInt().toString() + CELSIUS}"
                    },
                    style = TextStyle(fontSize = 65.sp),
                    color = Color.White
                )

                Text(
                    text = item.condition,
                    style = TextStyle(fontSize = 16.sp),
                    color = Color.White
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {
                        onClickSearch.invoke()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "im3",
                            tint = Color.White
                        )
                    }

                    Text(
                        modifier = Modifier.padding(top = 12.dp),
                        text = "${
                            item.maxTemp.toFloat().toInt()
                        }$CELSIUS/${item.minTemp.toFloat().toInt()}$CELSIUS",
                        style = TextStyle(fontSize = 16.sp),
                        color = Color.White
                    )

                    IconButton(onClick = {
                        onClickSyns.invoke()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_sync),
                            contentDescription = "im4",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun tabLayout(daysList: StateFlow<List<WeatherModel>>, currentDays: WeatherModel, viewModel: MainViewModel) {
    val weatherList = remember { mutableStateOf<List<WeatherModel>>(emptyList()) }

    val tabList = listOf(stringResource(id = R.string.hour), stringResource(id = R.string.days))
    val pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .padding(
                start = 5.dp,
                end = 5.dp
            )
            .clip(RoundedCornerShape(5.dp))
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            },
            backgroundColor = BlueLight,
            contentColor = Color.White
        ) {
            tabList.forEachIndexed { index, text ->
                Tab(
                    text = {
                        Text(text = text)
                    },
                    selectedContentColor = Color.DarkGray,
                    unselectedContentColor = Color.White,
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }

        }
        HorizontalPager(
            count = tabList.size,
            state = pagerState,
            modifier = Modifier.weight(1.0f)
        ) { index ->

            LaunchedEffect(index) {
                when (index) {
                    0 -> weatherList.value = viewModel.getWeatherByHour(currentDays.hours)
                    1 -> weatherList.value = daysList.value
                    else -> weatherList.value = daysList.value
                }
            }
            mainList(list = weatherList.value,viewModel)
        }
    }
}
