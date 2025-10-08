package com.weather.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.core.common.exception.LocationDeniedException
import com.weather.core.common.exception.LocationDeniedPermanentlyException
import com.weather.core.common.exception.LocationNotProvidedException
import com.weather.core.data.WeatherRepository
import com.weather.core.location.LocationProvider
import com.weather.core.model.LocationCoordinates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationProvider: LocationProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    /**
     * Gets weather information from the repository and updates the UI state accordingly.
     */
    fun getWeather(coordinates: LocationCoordinates? = null) {
        coordinates?.let {
            fetchWeather(it)
        } ?: run {
            locationProvider.getLocationCoordinates { coord ->
                if (coord == null) {
                    _uiState.value = HomeUiState.Error(LocationNotProvidedException())
                    return@getLocationCoordinates
                }
                fetchWeather(coord)
            }
        }
    }

    fun permissionDenied(shouldShowRationale: Boolean) {
        if (shouldShowRationale)
            _uiState.value = HomeUiState.Error(LocationDeniedException())
        else
            _uiState.value = HomeUiState.Error(LocationDeniedPermanentlyException())
    }


    private fun fetchWeather(coordinates: LocationCoordinates) {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            weatherRepository.getCurrentWeather(coordinates)
                .onSuccess { _uiState.value = HomeUiState.Success(it) }
                .onFailure { _uiState.value = HomeUiState.Error(it) }
        }
    }

}