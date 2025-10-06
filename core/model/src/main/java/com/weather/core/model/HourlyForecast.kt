package com.weather.core.model

data class HourlyForecast(
    val time: String,
    val temperature: String,
    val weatherIconUrl: String
)