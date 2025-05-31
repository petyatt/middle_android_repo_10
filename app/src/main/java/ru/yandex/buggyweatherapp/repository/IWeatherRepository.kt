package ru.yandex.buggyweatherapp.repository

import ru.yandex.buggyweatherapp.model.Location
import ru.yandex.buggyweatherapp.model.WeatherData

interface IWeatherRepository {
    suspend fun getWeatherData(location: Location): WeatherData
    suspend fun getWeatherByCity(cityName: String): WeatherData
}