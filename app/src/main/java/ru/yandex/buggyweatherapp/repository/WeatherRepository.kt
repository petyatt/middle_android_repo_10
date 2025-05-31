package ru.yandex.buggyweatherapp.repository

import ru.yandex.buggyweatherapp.api.WeatherApiService
import ru.yandex.buggyweatherapp.model.Location
import ru.yandex.buggyweatherapp.model.WeatherData
import ru.yandex.buggyweatherapp.model.WeatherResponse
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherApi: WeatherApiService
) : IWeatherRepository {

    private var cachedWeatherData: WeatherData? = null

    override suspend fun getWeatherData(location: Location): WeatherData {
        return try {
            val response = weatherApi.getCurrentWeather(location.latitude, location.longitude)
            val weatherData = parseWeatherResponse(response, location)
            cachedWeatherData = weatherData
            weatherData
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getWeatherByCity(cityName: String): WeatherData {
        return try {
            val response = weatherApi.getWeatherByCity(cityName)
            val location = Location(
                response.coord.lat,
                response.coord.lon,
                response.name
            )
            parseWeatherResponse(response, location)
        } catch (e: Exception) {
            throw e
        }
    }

    private fun parseWeatherResponse(response: WeatherResponse, location: Location): WeatherData {
        val main = response.main
        val wind = response.wind
        val sys = response.sys
        val weather = response.weather.first()

        return WeatherData(
            cityName = response.name,
            country = sys.country,
            temperature = main.temp,
            feelsLike = main.feelsLike,
            minTemp = main.temp,
            maxTemp = main.temp,
            humidity = main.humidity,
            pressure = main.pressure,
            windSpeed = wind.speed,
            windDirection = wind.deg,
            description = weather.description,
            icon = weather.icon,
            cloudiness = response.clouds?.all ?: 0,
            sunriseTime = sys.sunrise,
            sunsetTime = sys.sunset,
            timezone = response.timezone ?: 0,
            timestamp = response.dt,
            rawApiData = response.toString(),
            rain = response.rain?.oneHour,
            snow = response.snow?.oneHour
        )
    }
}