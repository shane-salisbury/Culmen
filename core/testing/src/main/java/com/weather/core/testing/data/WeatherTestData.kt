package com.weather.core.testing.data

import com.weather.core.model.DailyForecast
import com.weather.core.model.HourlyForecast
import com.weather.core.model.LocationCoordinates
import com.weather.core.model.Weather
import com.weather.core.network.model.NetworkCoordinates
import com.weather.core.network.model.NetworkMain
import com.weather.core.network.model.NetworkWeather
import com.weather.core.network.model.NetworkWeatherDescription
import com.weather.core.network.model.NetworkWind

val weatherTestData = Weather(
    city = "Los Angeles",
    feelsLike = "74°",
    temperature = "73° F",
    coordinates = LocationCoordinates(0.2, 0.2),
    highTemperature = "76°",
    lowTemperature = "71°",
    windSpeed = "1.99 mph",
    windDirection = "SW",
    weatherDescription = "cloudy",
    weatherIconUrl = "https://openweathermap.org/img/wn/ic0@2x.png",
    dailyForecastList = listOf(
        DailyForecast(
            date = "Monday, October 21",
            highTemperature = "77° F",
            lowTemperature = "73° F",
            weatherDescription = "cloudy",
            weatherIconUrl = "https://openweathermap.org/img/wn/ic1@2x.png",
            humidity = "33%",
            precipitationProbability = "18%"
        )
    )
)

val networkWeatherTestData = NetworkWeather(
    name = "Los Angeles",
    coordinates = NetworkCoordinates(0.2, 0.2),
    main = NetworkMain(temp = 72.9, feelsLike = 74.3, tempMin = 71.4, tempMax = 76.1, humidity = 23),
    wind = NetworkWind(speed = 1.99, deg = 225),
    weather = listOf(NetworkWeatherDescription("cloudy", "ic0"))
)