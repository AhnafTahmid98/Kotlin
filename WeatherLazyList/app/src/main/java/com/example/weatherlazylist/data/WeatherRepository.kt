package com.example.weatherlazylist.data

class WeatherRepository(
    private val api: WeatherApi,
    private val apiKey: String
) {

    suspend fun fetchWeather(city: String): WeatherItem {
        val response = api.getWeather(city = city, apiKey = apiKey)
        return WeatherItem(
            city = response.name,
            temperature = response.main.temp,
            humidity = response.main.humidity
        )
    }
}
