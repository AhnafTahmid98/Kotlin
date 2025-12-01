package com.example.weatherlazylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.weatherlazylist.data.WeatherItem
import com.example.weatherlazylist.data.WeatherRepository
import com.example.weatherlazylist.data.WeatherService
import com.example.weatherlazylist.ui.WeatherViewModel
import com.example.weatherlazylist.ui.WeatherViewModelFactory
import com.example.weatherlazylist.ui.theme.WeatherLazyListTheme

class MainActivity : ComponentActivity() {

    // TODO: put your real API key here
    private val apiKey = "083ab9341652268d97d24cca83b9c06a"

    private val weatherViewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory(
            WeatherRepository(
                api = WeatherService.api,
                apiKey = apiKey
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WeatherLazyListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherScreen(viewModel = weatherViewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    val items by viewModel.items.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    var cityText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Weather LazyList") }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = cityText,
                onValueChange = { cityText = it },
                label = { Text("City name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                )
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        viewModel.loadWeather(cityText)
                        cityText = ""
                    },
                    enabled = !isLoading,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Add city")
                }

                OutlinedButton(
                    onClick = { viewModel.clearList() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Clear list")
                }
            }

            if (isLoading) {
                Text("Loading...", style = MaterialTheme.typography.bodyMedium)
            }

            error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items) { item ->
                    WeatherRow(item)
                }
            }
        }
    }
}

@Composable
fun WeatherRow(item: WeatherItem) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(
                text = item.city,
                style = MaterialTheme.typography.titleMedium
            )
            Text("Temperature: %.1f °C".format(item.temperature))
            Text("Humidity: ${item.humidity}%")
        }
    }
}
