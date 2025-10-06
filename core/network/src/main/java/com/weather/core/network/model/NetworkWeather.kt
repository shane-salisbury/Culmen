package com.weather.core.network.model

import android.annotation.SuppressLint
import androidx.annotation.Keep
import com.weather.core.common.degreeToDirection
import com.weather.core.common.iconNameToUrl
import com.weather.core.common.withDegreeAndFahrenheitSymbol
import com.weather.core.common.withDegreeSymbol
import com.weather.core.common.withMphPostfix
import com.weather.core.model.LocationCoordinates
import com.weather.core.model.Weather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
@Keep
data class NetworkWeather(
    @SerialName("coord")
    val coordinates: NetworkCoordinates,
    @SerialName("weather")
    val weather: List<NetworkWeatherDescription>,
    @SerialName("main")
    val main: NetworkMain,
    @SerialName("wind")
    val wind: NetworkWind,
    @SerialName("name")
    val name: String
)

fun NetworkWeather.mapToWeatherWith(networkForecast: NetworkForecast): Weather {
    return Weather(
        city = name,
        temperature = main.temp.withDegreeAndFahrenheitSymbol(),
        weatherDescription = weather.first().description,
        weatherIconUrl = weather.first().icon.iconNameToUrl(),
        feelsLike = main.feelsLike.withDegreeSymbol(),
        highTemperature = main.tempMax.withDegreeSymbol(),
        lowTemperature = main.tempMin.withDegreeSymbol(),
        windSpeed = wind.speed.withMphPostfix(),
        windDirection = wind.deg.degreeToDirection(),
        dailyForecastList = networkForecast.mapToDailyForecastList(),
        coordinates = LocationCoordinates(
            latitude = coordinates.lat,
            longitude = coordinates.lon
        )
    )

}