package com.weather.core.network.model

import com.google.common.truth.Truth.assertThat
import com.weather.core.testing.data.networkHourlyForecastTestData
import com.weather.core.testing.data.networkWeatherTestData
import com.weather.core.testing.data.weatherTestData
import io.mockk.every
import io.mockk.mockkObject
import kotlinx.datetime.TimeZone
import org.junit.Test

class NetworkWeatherTest {

    @Test
    fun mapToWeatherWithNetworkForecast_shouldCreateWeather() {
        // Given
        mockkObject(TimeZone)
        val networkWeather = networkWeatherTestData
        val networkHourlyForecast = networkHourlyForecastTestData
        val expectedResult = weatherTestData

        every { TimeZone.currentSystemDefault() } returns TimeZone.UTC

        // When
        val weather = networkWeather.mapToWeatherWith(networkHourlyForecast)

        // Then
        assertThat(weather).isEqualTo(expectedResult)
    }
}