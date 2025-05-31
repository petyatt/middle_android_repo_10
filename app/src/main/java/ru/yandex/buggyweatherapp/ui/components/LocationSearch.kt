import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import ru.yandex.buggyweatherapp.viewmodel.WeatherViewModel

@Composable
fun LocationSearchWithViewModel(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    var searchText by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()

    OutlinedTextField(
        value = searchText,
        onValueChange = { searchText = it },
        label = { Text("Search city") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        trailingIcon = {
            IconButton(onClick = {
                if (searchText.isNotBlank()) {
                    viewModel.searchWeatherByCity(searchText)
                }
            }) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
        }
    )

    if (uiState.isLoading) {
        LinearProgressIndicator(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp))
    }

    uiState.weatherData?.let {
        Text(
            text = "Weather for ${it.cityName}: ${it.temperature}°C",
            modifier = Modifier.padding(16.dp)
        )
    }

    uiState.error?.let {
        Text(
            text = "Error: $it",
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(16.dp)
        )
    }
}