package com.weather.core.testing.data

import com.weather.core.model.DailyForecast
import com.weather.core.model.HourlyForecast
import com.weather.core.model.HourlyForecastForDaily
import com.weather.core.network.model.NetworkForecast
import com.weather.core.network.model.NetworkForecastItem
import com.weather.core.network.model.NetworkMain
import com.weather.core.network.model.NetworkWeatherDescription
import com.weather.core.network.model.NetworkWind
import kotlinx.datetime.LocalDate

val networkForecastTestData = NetworkForecast(
    list=listOf(
        NetworkForecastItem(
            dt=1729533600,
            main=NetworkMain(temp=65.62, feelsLike=62.55, tempMin=63.73, tempMax=65.62, humidity = 50),
            weather= listOf(NetworkWeatherDescription(description="broken clouds", icon="04n")),
            pop = 0.23),
        NetworkForecastItem(
            dt=1729544400,
            main=NetworkMain(temp=62.02, feelsLike=59.0, tempMin=59.74, tempMax=62.02, humidity = 34),
            weather=listOf(NetworkWeatherDescription(description="scattered clouds", icon="03n")),
            pop = 0.32),
        NetworkForecastItem(
            dt=1729555200,
            main=NetworkMain(temp=56.73, feelsLike=53.71, tempMin=56.73, tempMax=56.73, humidity = 23),
            weather=listOf(NetworkWeatherDescription(description="clear sky", icon="01n")),
            pop = 0.49),
        NetworkForecastItem(
            dt=1729566000,
            main=NetworkMain(temp=54.97, feelsLike=51.62, tempMin=54.97, tempMax=54.97, humidity = 21),
            weather=listOf(NetworkWeatherDescription(description="clear sky", icon="01d")),
            pop = 0.54),
        NetworkForecastItem(
            dt=1729576800,
            main=NetworkMain(temp=59.65, feelsLike=56.39, tempMin=59.65, tempMax=59.65, humidity = 23),
            weather=listOf(NetworkWeatherDescription(description="clear sky", icon="01d")),
            pop = 0.34),
        NetworkForecastItem(
            dt=1729587600,
            main=NetworkMain(temp=62.92, feelsLike=59.99, tempMin=62.92, tempMax=62.92, humidity = 43),
            weather=listOf(NetworkWeatherDescription(description="clear sky", icon="01d")),
            pop = 0.32),
        NetworkForecastItem(
            dt=1729598400,
            main=NetworkMain(temp=63.27, feelsLike=60.28, tempMin=63.27, tempMax=63.27, humidity = 54),
            weather=listOf(NetworkWeatherDescription(description="clear sky", icon="01d")),
            pop = 0.31),
        NetworkForecastItem(
            dt=1729609200,
            main=NetworkMain(temp=61.66, feelsLike=58.57, tempMin=61.66, tempMax=61.66, humidity = 32),
            weather=listOf(NetworkWeatherDescription(description="clear sky", icon="01n")),
            pop = 0.12),
    )
)

val networkHourlyForecastTestData = NetworkForecast(
    list = listOf(
        NetworkForecastItem(
            dt = 1729522800,
            NetworkMain(temp = 73.3, feelsLike = 74.6, tempMin = 71.4, tempMax = 76.5, humidity = 34),
            weather = listOf(NetworkWeatherDescription("cloudy", "ic1")),
            pop = 0.12
        ),
        NetworkForecastItem(
            dt = 1729533600,
            NetworkMain(temp = 77.2, feelsLike = 74.6, tempMin = 71.4, tempMax = 76.5, humidity = 32),
            weather = listOf(NetworkWeatherDescription("cloudy", "ic2")),
            pop = 0.23
        )
    )
)

val forecastListTestData = listOf(
    DailyForecast(
        date = "Monday",
        highTemperature = "76°",
        lowTemperature = "71°",
        weatherDescription = "Partly cloudy",
        weatherIconUrl = "",
        humidity = "23%",
        precipitationProbability = "12%"
    ),
    DailyForecast(
        date = "Tuesday",
        highTemperature = "76°",
        lowTemperature = "71°",
        weatherDescription = "Sunny",
        weatherIconUrl = "",
        humidity = "23%",
        precipitationProbability = "12%"
    ),
    DailyForecast(
        date = "Wednesday",
        highTemperature = "76°",
        lowTemperature = "71°",
        weatherDescription = "Rainy",
        weatherIconUrl = "",
        humidity = "23%",
        precipitationProbability = "12%"
    )
)

val dailyForecastTestData = listOf(
    DailyForecast(
        date="Monday, October 21",
        highTemperature="66° F",
        lowTemperature="62° F",
        weatherDescription="broken clouds",
        weatherIconUrl="https://openweathermap.org/img/wn/04n@2x.png",
        humidity = "42%",
        precipitationProbability = "28%"
    ),
    DailyForecast(
        date="Tuesday, October 22",
        highTemperature="63° F",
        lowTemperature="55° F",
        weatherDescription="clear sky",
        weatherIconUrl="https://openweathermap.org/img/wn/01d@2x.png",
        humidity = "32%",
        precipitationProbability = "35%"
    )
)

val hourlyForecastForDaily = HourlyForecastForDaily(
    localDate = LocalDate(2024, 10, 21),
    temperature=65.62,
    weatherDescription="broken clouds",
    weatherIconName="04n",
    humidity = 50,
    precipitationProbability = 0.23
)