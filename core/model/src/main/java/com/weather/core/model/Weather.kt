package com.weather.core.model

data class Weather(
    val city: String,
    val temperature: String,
    val weatherDescription: String,
    val weatherIconUrl: String,
    val feelsLike: String,
    val highTemperature: String,
    val lowTemperature: String,
    val windSpeed: String,
    val windDirection: String,
    val dailyForecastList: List<DailyForecast>,
    val coordinates: LocationCoordinates,
)