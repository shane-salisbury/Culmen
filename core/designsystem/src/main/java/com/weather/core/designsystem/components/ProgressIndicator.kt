package com.weather.core.designsystem.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BoxScope.WeatherProgressIndicator(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
            .align(Alignment.TopCenter)
            .padding(top = 32.dp),
    )
}

@Composable
fun BoxScope.WeatherProgressIndicator(
    progress: () -> Float,
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        progress = progress,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
            .align(Alignment.TopCenter)
            .padding(top = 32.dp),
    )
}