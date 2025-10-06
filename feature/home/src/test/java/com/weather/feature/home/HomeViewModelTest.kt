package com.weather.feature.home

import com.google.common.truth.Truth.assertThat
import com.weather.core.common.exception.LocationNotProvidedException
import com.weather.core.data.WeatherRepository
import com.weather.core.location.LocationProvider
import com.weather.core.model.LocationCoordinates
import com.weather.core.testing.data.coordinatesTestData
import com.weather.core.testing.data.weatherTestData
import com.weather.core.testing.location.TestLocationProvider
import com.weather.core.testing.rule.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val weatherRepository: WeatherRepository = mockk(relaxed = true)

    private fun createViewModel(coordinates: LocationCoordinates? = null): HomeViewModel {
        val locationProvider: LocationProvider = TestLocationProvider(coordinates)
        return HomeViewModel(weatherRepository, locationProvider)
    }

    @Test
    fun uiState_shouldInitiallyBeLoading() {
        val viewModel = createViewModel()
        assertThat(viewModel.uiState.value).isEqualTo(HomeUiState.Loading)
    }

    @Test
    fun getWeather_shouldUpdateUiStateToSuccess_whenRepositoryReturnsSuccess() = runTest {
        // Given
        val viewModel = createViewModel(coordinatesTestData)
        val weather = weatherTestData

        coEvery {
            weatherRepository.getCurrentWeather(any())
        } returns Result.success(weather)

        // When
        viewModel.getWeather()

        // Then
        assertThat(viewModel.uiState.value).isEqualTo(HomeUiState.Success(weather))
        coVerify { weatherRepository.getCurrentWeather(any()) }
    }

    @Test
    fun getWeather_shouldUpdateUiStateToError_whenRepositoryReturnsError() = runTest {
        // Given
        val viewModel = createViewModel(coordinatesTestData)
        val exception = Exception("Connection failed")

        coEvery { weatherRepository.getCurrentWeather(any()) } returns Result.failure(exception)

        // When
        viewModel.getWeather()

        // Then
        assertThat(viewModel.uiState.value).isEqualTo(HomeUiState.Error(exception))
        coVerify { weatherRepository.getCurrentWeather(any()) }
    }

    @Test
    fun getWeather_shouldUpdateUiStateToError_whenLocationNotProvided() = runTest {
        // Given
        val viewModel = createViewModel(coordinates = null)

        // When
        viewModel.getWeather()

        // Then
        assertThat(viewModel.uiState.value).isInstanceOf(HomeUiState.Error::class.java)
        assertThat((viewModel.uiState.value as HomeUiState.Error).exception)
            .isInstanceOf(LocationNotProvidedException::class.java)
    }
}