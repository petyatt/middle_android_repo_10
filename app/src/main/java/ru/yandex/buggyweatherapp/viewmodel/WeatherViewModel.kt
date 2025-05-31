package ru.yandex.buggyweatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.yandex.buggyweatherapp.repository.LocationRepository
import ru.yandex.buggyweatherapp.repository.WeatherRepository
import ru.yandex.buggyweatherapp.utils.WeatherUiState
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState

    init {
        fetchCurrentLocationWeather()
    }

    fun fetchCurrentLocationWeather() {
        _uiState.update { it.copy(isLoading = true, error = null) }

        locationRepository.getCurrentLocation { location ->
            if (location != null) {
                val city = locationRepository.getCityNameFromLocation(location) ?: "Unknown"

                viewModelScope.launch {
                    try {
                        val weather = weatherRepository.getWeatherData(location)
                        _uiState.update {
                            it.copy(
                                weatherData = weather,
                                cityName = city,
                                isLoading = false,
                                error = null
                            )
                        }
                    } catch (e: Exception) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = e.message ?: "Unknown error"
                            )
                        }
                    }
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Unable to get current location"
                    )
                }
            }
        }
    }

    fun searchWeatherByCity(cityName: String) {
        if (cityName.isBlank()) return

        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            try {
                val weather = weatherRepository.getWeatherByCity(cityName)
                _uiState.update {
                    it.copy(
                        weatherData = weather,
                        cityName = cityName,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error"
                    )
                }
            }
        }
    }

    fun toggleFavorite() {
        val currentWeather = _uiState.value.weatherData ?: return
        val updatedWeather = currentWeather.copy(isFavorite = !currentWeather.isFavorite)
        _uiState.update {
            it.copy(weatherData = updatedWeather)
        }
    }
}