package ru.yandex.buggyweatherapp.model

data class WeatherResponse(
    val name: String,
    val coord: Coord,
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind,
    val sys: Sys,
    val clouds: Clouds?,
    val dt: Long,
    val timezone: Int?,
    val rain: Precipitation?,
    val snow: Precipitation?
)

data class Coord(
    val lon: Double,
    val lat: Double
)

data class Weather(
    val main: String,
    val description: String,
    val icon: String
)

data class Main(
    val temp: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Int,
    val humidity: Int
)

data class Wind(
    val speed: Double,
    val deg: Int
)

data class Clouds(
    val all: Int
)

data class Sys(
    val country: String,
    val sunrise: Long,
    val sunset: Long
)

data class Precipitation(
    val oneHour: Double?
)

data class ForecastItem(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind,
    val dtTxt: String
)

data class City(
    val name: String,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)

data class ForecastResponse(
    val list: List<ForecastItem>,
    val city: City
)