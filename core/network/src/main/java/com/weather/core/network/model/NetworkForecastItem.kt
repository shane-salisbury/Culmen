package com.weather.core.network.model

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.Keep
import com.weather.core.common.epochSecondToAmPmTime
import com.weather.core.common.epochSecondToLocalDate
import com.weather.core.common.iconNameToUrl
import com.weather.core.common.withDegreeAndFahrenheitSymbol
import com.weather.core.model.HourlyForecast
import com.weather.core.model.HourlyForecastForDaily
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
@Keep
data class NetworkForecastItem(
    @SerialName("dt")
    val dt: Long,
    @SerialName("main")
    val main: NetworkMain,
    @SerialName("weather")
    val weather: List<NetworkWeatherDescription>,
    @SerialName("pop")
    val pop: Double
)

/**
 * Maps a [NetworkForecastItem] to a [HourlyForecastForDaily] domain model.
 * This function is used to transform the raw network data for a specific time interval
 * into a more detailed forecast model used within the daily forecast view.
 *
 * @return A [HourlyForecastForDaily] object containing detailed weather information
 * such as date, temperature, weather description, icon name, humidity, and
 * the probability of precipitation.
 */
fun NetworkForecastItem.mapToHourlyForecastForDaily(): HourlyForecastForDaily {
    return HourlyForecastForDaily(
        localDate = dt.epochSecondToLocalDate(),
        temperature = main.temp,
        weatherDescription = if (weather.isNotEmpty()) weather.first().description else "",
        weatherIconName = if (weather.isNotEmpty()) weather.first().icon else "",
        humidity = main.humidity,
        precipitationProbability = pop
    )
}