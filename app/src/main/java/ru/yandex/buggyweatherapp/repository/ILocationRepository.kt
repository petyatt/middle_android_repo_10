package ru.yandex.buggyweatherapp.repository

import ru.yandex.buggyweatherapp.model.Location

interface ILocationRepository {
    fun getCurrentLocation(callback: (Location?) -> Unit)
    fun getCityNameFromLocation(location: Location): String?
    fun startLocationTracking()
}