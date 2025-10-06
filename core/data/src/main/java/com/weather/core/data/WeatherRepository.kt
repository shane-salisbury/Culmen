package com.weather.core.data

import com.weather.core.model.DailyForecast
import com.weather.core.model.LocationCoordinates
import com.weather.core.model.Weather

interface WeatherRepository {
    suspend fun getCurrentWeather(coordinates: LocationCoordinates): Result<Weather>

    suspend fun getForecast(coordinates: LocationCoordinates): Result<List<DailyForecast>>
}