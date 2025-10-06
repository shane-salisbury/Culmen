package com.weather.core.data

import com.weather.core.common.suspendRunCatching
import com.weather.core.model.DailyForecast
import com.weather.core.model.LocationCoordinates
import com.weather.core.model.Weather
import com.weather.core.network.WeatherService
import com.weather.core.network.model.mapToDailyForecastList
import com.weather.core.network.model.mapToWeatherWith
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService
) : WeatherRepository {
    override suspend fun getCurrentWeather(coordinates: LocationCoordinates): Result<Weather> =
        coroutineScope {
            suspendRunCatching {
                val networkWeather = weatherService.getCurrentWeather(
                    latitude = coordinates.latitude,
                    longitude = coordinates.longitude,
                    units = "imperial"
                )

                val networkForecast = weatherService.getForecast(
                    latitude = coordinates.latitude,
                    longitude = coordinates.longitude,
                    units = "imperial"
                )

                networkWeather.mapToWeatherWith(networkForecast)
            }
        }

    override suspend fun getForecast(coordinates: LocationCoordinates): Result<List<DailyForecast>> =
        suspendRunCatching {
            val networkForecast = weatherService.getForecast(
                latitude = coordinates.latitude,
                longitude = coordinates.longitude,
                units = "imperial"
            )

            networkForecast.mapToDailyForecastList()
        }
}