package com.example.weatherlazylist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherlazylist.data.WeatherItem
import com.example.weatherlazylist.data.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _items = MutableStateFlow<List<WeatherItem>>(emptyList())
    val items: StateFlow<List<WeatherItem>> = _items

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadWeather(city: String) {
        val trimmed = city.trim()
        if (trimmed.isBlank()) return

        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                val item = repository.fetchWeather(trimmed)
                _items.value = _items.value + item
            } catch (e: Exception) {
                _error.value = e.message ?: "Error loading weather"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearList() {
        _items.value = emptyList()
    }
}

/** Factory so we can pass a WeatherRepository from MainActivity */
class WeatherViewModelFactory(
    private val repository: WeatherRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
