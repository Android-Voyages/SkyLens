package com.observer.weatherappcompose.presentation.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.observer.weatherappcompose.domain.entity.WeatherModel
import com.observer.weatherappcompose.presentation.ui.theme.BlueLight
import com.observer.weatherappcompose.presentation.viewModel.MainViewModel
import com.observer.weatherappcompose.utills.Constant


@Composable
fun mainList(list: List<WeatherModel>, viewModel: MainViewModel) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(
            list
        ) { _, item ->
            listItem(item, viewModel)
        }
    }
}

@Composable
fun listItem(item: WeatherModel, viewModel: MainViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp)
            .clickable {
                if (item.hours.isEmpty()) {
                    return@clickable
                } else {
                    viewModel.updateCurrentDays(item)
                }
            },
        backgroundColor = BlueLight,
        elevation = 0.dp,
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        top = 5.dp,
                        bottom = 5.dp
                    )
            ) {
                Text(text = item.time)
                Text(text = item.condition, color = Color.White)
            }
            Text(
                text = item.currentTemp.ifEmpty {
                    "${
                        item.maxTemp.toFloat().toInt().toString() + Constant.CELSIUS
                    }/${item.minTemp.toFloat().toInt().toString() + Constant.CELSIUS}"
                },
                color = Color.White,
                style = TextStyle(fontSize = 25.sp)
            )
            AsyncImage(
                model = "https:${item.icon}",
                contentDescription = "im5",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(35.dp)
            )

        }
    }
}


@Composable
fun alertDialogSearch(viewModel: MainViewModel, onSubmit: (String) -> Unit) {

    val text = viewModel.dialogText.collectAsState()

    AlertDialog(

        onDismissRequest = {
            viewModel.updateDialogState(false)
        },
        confirmButton = {
            TextButton(onClick = {
                onSubmit.invoke(text.value)
                viewModel.updateDialogState(false)
            }) {
                Text("Search")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                viewModel.updateDialogState(false)
            }) {
                Text(text = "Cancel")
            }
        },
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Please enter the city name in English for better search results",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black // Вы можете настроить цвет текста по вашему вкусу
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp) // Добавляем отступы для красоты
                )

                TextField(
                    value = text.value,
                    onValueChange = {
                        viewModel.updateDialogState(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(
                            text = "Input city",
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.Gray // Вы можете настроить цвет подсказки
                            )
                        )
                    }
                )

            }

        }
    )
}
