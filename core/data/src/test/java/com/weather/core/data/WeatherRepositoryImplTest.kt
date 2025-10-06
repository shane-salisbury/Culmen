package com.weather.core.data

import android.util.Log
import com.google.common.truth.Truth.assertThat
import com.weather.core.network.WeatherService
import com.weather.core.testing.data.coordinatesTestData
import com.weather.core.testing.data.dailyForecastTestData
import com.weather.core.testing.data.networkForecastTestData
import com.weather.core.testing.data.networkHourlyForecastTestData
import com.weather.core.testing.data.networkWeatherTestData
import com.weather.core.testing.data.weatherTestData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.TimeZone
import org.junit.Before
import org.junit.Test

class WeatherRepositoryImplTest {

    private val weatherService: WeatherService = mockk(relaxed = true)

    private lateinit var repository: WeatherRepositoryImpl

    @Before
    fun setUp() {
        repository = WeatherRepositoryImpl(weatherService)
    }

    @Test
    fun getCurrentWeather_shouldReturnSuccess_whenNetworkCallSucceeds() = runTest {
        // Given
        mockkObject(TimeZone)
        val networkWeather = networkWeatherTestData
        val networkHourlyForecast = networkHourlyForecastTestData

        val expectedResult = weatherTestData

        every { TimeZone.currentSystemDefault() } returns TimeZone.UTC
        coEvery { weatherService.getCurrentWeather(any(), any(), any()) } returns networkWeather
        coEvery { weatherService.getForecast(any(), any(), any()) } returns networkHourlyForecast

        // When
        val result = repository.getCurrentWeather(coordinatesTestData)

        // Then
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(expectedResult)
        coVerify { weatherService.getCurrentWeather(any(), any(), any()) }
        coVerify { weatherService.getForecast(any(), any(), any()) }
    }

    @Test
    fun getCurrentForecast_shouldReturnSuccess_whenNetworkCallSucceeds() = runTest {
        // Given
        mockkObject(TimeZone)
        val networkDailyForecast = networkForecastTestData

        val expectedResult = dailyForecastTestData

        every { TimeZone.currentSystemDefault() } returns TimeZone.UTC
        coEvery { weatherService.getForecast(any(), any(), any()) } returns networkDailyForecast

        // When
        val result = repository.getForecast(coordinatesTestData)

        // Then
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(expectedResult)
        coVerify { weatherService.getForecast(any(), any(), any()) }
    }

    @Test
    fun getCurrentWeather_shouldReturnError_whenNetworkCallFails() = runTest {
        // Given
        mockkStatic(Log::class)

        coEvery {
            weatherService.getCurrentWeather(any(), any(), any())
        } throws Exception("Network error")
        every { Log.i(any(), any(), any()) } returns 0

        // When
        val result = repository.getCurrentWeather(coordinatesTestData)

        // Then
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(Exception::class.java)
        coVerify { weatherService.getCurrentWeather(any(), any(), any()) }
    }
}