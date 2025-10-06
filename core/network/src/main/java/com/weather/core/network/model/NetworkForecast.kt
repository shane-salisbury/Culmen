package com.weather.core.network.model

import android.annotation.SuppressLint
import androidx.annotation.Keep
import com.weather.core.common.iconNameToUrl
import com.weather.core.common.toDateForDailyForecast
import com.weather.core.common.withDegreeAndFahrenheitSymbol
import com.weather.core.model.DailyForecast
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.roundToInt

@SuppressLint("UnsafeOptInUsageError")
@Serializable
@Keep
data class NetworkForecast(
    @SerialName("list")
    val list: List<NetworkForecastItem>
)

/**
 * Maps a [NetworkForecast] object, which contains a list of 3-hour interval forecast items,
 * to a list of [DailyForecast] objects.
 *
 * This function processes the raw forecast data by:
 * 1. Mapping each 3-hour forecast item to an intermediate hourly representation.
 * 2. Grouping these items by their local date.
 * 3. For each day's group of items, it calculates:
 *    - The highest temperature (`highTemperature`).
 *    - The lowest temperature (`lowTemperature`).
 *    - The most frequent weather description for the day.
 *    - The most frequent weather icon for the day.
 *    - The average humidity.
 *    - The average probability of precipitation.
 * 4. Finally, it constructs a [DailyForecast] object for each day with the computed values,
 *    formatting them for display.
 *
 * @return A list of [DailyForecast] objects, each representing the forecast for a single day.
 */
fun NetworkForecast.mapToDailyForecastList(): List<DailyForecast> {
    return list
        .map { it.mapToHourlyForecastForDaily() }
        .groupBy { it.localDate }
        .map { (localDate, items) ->
            val highTemperature = items.maxOf { it.temperature }
            val lowTemperature = items.minOf { it.temperature }

            val weatherDescription = items
                .groupingBy { it.weatherDescription }
                .eachCount()
                .maxBy { it.value }
                .key

            val weatherIconUrl = items
                .groupingBy { it.weatherIconName }
                .eachCount()
                .maxBy { it.value }
                .key

            val humidityAvg = items.sumOf { it.humidity } / items.size
            val humidity = "${humidityAvg}%"
            val popAvg = items.sumOf { it.precipitationProbability } / items.size
            val pop = "${(popAvg * 100).roundToInt()}%"

            DailyForecast(
                date = localDate.toDateForDailyForecast(),
                highTemperature = highTemperature.withDegreeAndFahrenheitSymbol(),
                lowTemperature = lowTemperature.withDegreeAndFahrenheitSymbol(),
                weatherDescription = weatherDescription,
                weatherIconUrl = weatherIconUrl.iconNameToUrl(),
                humidity = humidity,
                precipitationProbability = pop
            )
        }
}
