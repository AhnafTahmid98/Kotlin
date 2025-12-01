package com.example.weatherlazylist.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

// ---- DTOs that match the JSON from OpenWeather ----
data class MainInfo(
    val temp: Double,
    val humidity: Int
)

data class WeatherResponse(
    val name: String,   // city name
    val main: MainInfo
)

// What we use in the UI list
data class WeatherItem(
    val city: String,
    val temperature: Double,
    val humidity: Int
)

interface WeatherApi {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse
}

object WeatherService {
    val api: WeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
}
