package com.weather.core.network.model

import com.google.common.truth.Truth.assertThat
import com.weather.core.testing.data.hourlyForecastForDaily
import com.weather.core.testing.data.networkForecastTestData
import io.mockk.every
import io.mockk.mockkObject
import kotlinx.datetime.TimeZone
import org.junit.Test

class NetworkForecastItemTest {

    @Test
    fun mapToHourlyForecastForDaily_shouldCreateHourlyForecastForDaily() {
        // Given
        mockkObject(TimeZone)
        val networkForecastItem = networkForecastTestData.list[0]
        val expectedResult = hourlyForecastForDaily

        every { TimeZone.currentSystemDefault() } returns TimeZone.UTC

        // When
        val hourlyForecastForDaily = networkForecastItem.mapToHourlyForecastForDaily()

        // Then
        assertThat(hourlyForecastForDaily).isEqualTo(expectedResult)
    }
}