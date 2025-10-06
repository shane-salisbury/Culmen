package com.weather.feature.home

import com.weather.core.model.Weather

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Success(val weather: Weather) : HomeUiState

    data class Error(val exception: Throwable?) : HomeUiState
}