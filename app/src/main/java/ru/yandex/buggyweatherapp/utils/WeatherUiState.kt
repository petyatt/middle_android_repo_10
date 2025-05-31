package ru.yandex.buggyweatherapp.utils

import ru.yandex.buggyweatherapp.model.WeatherData

data class WeatherUiState(
    val weatherData: WeatherData? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val cityName: String = ""
)
