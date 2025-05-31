package ru.yandex.buggyweatherapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.yandex.buggyweatherapp.R
import ru.yandex.buggyweatherapp.model.WeatherData
import ru.yandex.buggyweatherapp.utils.WeatherIconMapper

@Composable
fun WeatherCard(
    weatherData: WeatherData,
    cityName: String?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = cityName ?: weatherData.cityName,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(WeatherIconMapper.getIconUrl(weatherData.icon))
                        .crossfade(true)
                        .build(),
                    contentDescription = weatherData.description,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(80.dp)
                )

                Text(
                    text = stringResource(R.string.temperature, weatherData.temperature.toInt()),
                    style = MaterialTheme.typography.headlineLarge
                )
            }

            Text(
                text = stringResource(
                    R.string.description,
                    weatherData.description.replaceFirstChar { it.uppercase() }
                ),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                val feelsLikeLabel = context.getString(R.string.feels_like)
                val feelsLikeValue = context.getString(R.string.temperature, weatherData.feelsLike.toInt())
                val minMaxTemp = context.getString(R.string.min_max_temp, weatherData.minTemp.toInt(), weatherData.maxTemp.toInt())
                val humidity = context.getString(R.string.humidity, weatherData.humidity)
                val pressure = context.getString(R.string.pressure, weatherData.pressure)
                val wind = context.getString(R.string.wind, weatherData.windSpeed)
                val sunrise = context.getString(R.string.sunrise, WeatherIconMapper.formatTimestamp(weatherData.sunriseTime))
                val sunset = context.getString(R.string.sunset, WeatherIconMapper.formatTimestamp(weatherData.sunsetTime))

                val items = listOf(
                    feelsLikeLabel to feelsLikeValue,
                    minMaxTemp to "",
                    humidity to "",
                    pressure to "",
                    wind to "",
                    sunrise to "",
                    sunset to ""
                )

                items(items.filter { it.first.isNotEmpty() }) { (label, value) ->
                    if (value.isEmpty()) {
                        WeatherDataSingleRow(label)
                    } else {
                        WeatherDataRow(label, value)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
@Composable
private fun WeatherDataRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}
@Composable
private fun WeatherDataSingleRow(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(text = text, style = MaterialTheme.typography.bodyMedium)
    }
}