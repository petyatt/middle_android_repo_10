package ru.yandex.buggyweatherapp.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.yandex.buggyweatherapp.BuildConfig
import ru.yandex.buggyweatherapp.model.ForecastResponse
import ru.yandex.buggyweatherapp.model.WeatherResponse

interface WeatherApiService {
    
    
    companion object {
        const val API_KEY = BuildConfig.OPEN_WEATHER_API_KEY
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }
    
    
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = API_KEY,
        @Query("units") units: String = "metric"
    ): WeatherResponse
    
    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String = API_KEY,
        @Query("units") units: String = "metric"
    ): WeatherResponse
    
    @GET("forecast")
    suspend fun getForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = API_KEY,
        @Query("units") units: String = "metric"
    ): ForecastResponse
}