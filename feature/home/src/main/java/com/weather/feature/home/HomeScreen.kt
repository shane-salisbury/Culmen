package com.weather.feature.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.weather.core.common.exception.LocationDeniedException
import com.weather.core.common.exception.LocationDeniedPermanentlyException
import com.weather.core.designsystem.components.WeatherProgressIndicator
import com.weather.core.designsystem.theme.DefaultPadding
import com.weather.core.designsystem.theme.ExtraSmallPadding
import com.weather.core.designsystem.theme.MediumPadding
import com.weather.core.designsystem.theme.SmallPadding
import com.weather.core.designsystem.theme.WeatherIconSize
import com.weather.core.designsystem.theme.WeatherTheme
import com.weather.core.model.DailyForecast
import com.weather.core.model.LocationCoordinates
import com.weather.core.model.Weather

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var askedOnce by rememberSaveable { mutableStateOf(false) }

    val locationPermissionState = rememberPermissionState(
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )


    // Ask for permission the first time we enter this screen (only once)
    LaunchedEffect(Unit) {
        if (!locationPermissionState.status.isGranted && !askedOnce) {
            askedOnce = true
            locationPermissionState.launchPermissionRequest()
        }
    }

    // React to permission status *after* request has been made / user has interacted
    LaunchedEffect(locationPermissionState.status) {
        when (val status = locationPermissionState.status) {
            PermissionStatus.Granted -> {
                viewModel.getWeather()
            }
            is PermissionStatus.Denied -> {
                Log.d("HomeScreen", "Location permission denied $status")
                if (status.shouldShowRationale) {
                    // User denied but not permanently: show rationale UI/state
                    viewModel.permissionDenied(true)
                } else {
                    // Permanently denied (Don't ask again) or not granted after prompt
                    viewModel.permissionDenied(false)
                }
            }
        }
    }

    HomeScreen(
        uiState = uiState,
        getWeather = viewModel::getWeather,
        requestPermission = { locationPermissionState.launchPermissionRequest() },
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    uiState: HomeUiState,
    getWeather: (location: LocationCoordinates?) -> Unit,
    requestPermission: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val pullToRefreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }
    var showSearch by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(uiState) {
        isRefreshing = false
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    val title = when (uiState) {
                        is HomeUiState.Success -> uiState.weather.city
                        else -> stringResource(id = R.string.app_name)
                    }
                    Text(title, maxLines = 1, overflow = TextOverflow.Ellipsis)
                },
                actions = {
                    IconToggleButton(
                        checked = showSearch,
                        onCheckedChange = { showSearch = it },
                        colors = IconButtonDefaults.iconToggleButtonColors(
                            checkedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            checkedContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.0f),
                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = if (showSearch) "Hide search" else "Show search"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true
                getWeather(null)
            },
            state = pullToRefreshState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState) {
                HomeUiState.Loading -> WeatherProgressIndicator()
                is HomeUiState.Error -> ErrorView(
                    exception = uiState.exception,
                    retry = {
                        when (uiState.exception) {
                            is LocationDeniedException -> requestPermission()
                            is LocationDeniedPermanentlyException -> openAppSettings(context)
                            else -> getWeather(null)
                        }
                    }
                )

                is HomeUiState.Success -> HomeDetails(
                    weather = uiState.weather,
                    showSearch = showSearch,
                    onSearch = { lat, lon ->
                        val latD = lat.toDoubleOrNull()
                        val lonD = lon.toDoubleOrNull()
                        if (latD == null || lonD == null) {
                            getWeather(null)
                        } else {
                            val coordinates = LocationCoordinates(
                                latitude = latD,
                                longitude = lonD
                            )
                            getWeather(coordinates)
                        }
                        showSearch = false
                    },
                    onUseMyLocation = { getWeather(null) },
                    modifier = Modifier,
                )
            }
        }
    }
}

@Composable
private fun HomeDetails(
    weather: Weather,
    showSearch: Boolean,
    onSearch: (String, String) -> Unit,
    onUseMyLocation: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(DefaultPadding),
        contentPadding = PaddingValues(vertical = DefaultPadding),
        modifier = modifier.fillMaxSize()
    ) {
        item {
            AnimatedVisibility(showSearch) {
                SearchComponent(
                    modifier = Modifier.padding(top = ExtraSmallPadding),
                    defaultLocation = weather.coordinates,
                    onSearch = onSearch,
                    onUseMyLocation = onUseMyLocation
                )
            }
        }
        weatherItems(weather)
    }
}

private fun LazyListScope.weatherItems(
    weather: Weather
) {
    item {
        WeatherHeader(weather)
    }

    items(weather.dailyForecastList, key = { it.date }) {
        DailyForecastCard(dailyForecast = it)
    }
}

@Composable
fun WeatherHeader(weather: Weather) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(DefaultPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            AsyncImage(
                model = weather.weatherIconUrl,
                contentDescription = null,
                modifier = Modifier.size(WeatherIconSize)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = weather.weatherDescription)
        }
        Text(
            text = weather.temperature,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Text(text = stringResource(R.string.feels_like, weather.feelsLike))
        Text(
            text = stringResource(
                R.string.high_low,
                weather.highTemperature,
                weather.lowTemperature
            ), style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun SearchComponent(
    modifier: Modifier = Modifier,
    defaultLocation: LocationCoordinates? = null,
    onSearch: (String, String) -> Unit,
    onUseMyLocation: () -> Unit
) {
    var lat by rememberSaveable { mutableStateOf("") }
    var lon by rememberSaveable { mutableStateOf("") }
    val isSearchEnabled by remember(lat, lon) {
        derivedStateOf { lat.isNotBlank() && lon.isNotBlank() }
    }
    val numberRegex = Regex("^-?[0-9]*[.]?[0-9]*$")

    LaunchedEffect(defaultLocation) {
        lat = defaultLocation?.latitude?.toString().orEmpty()
        lon = defaultLocation?.longitude?.toString().orEmpty()
    }

    ElevatedCard(
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = DefaultPadding, vertical = SmallPadding)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MediumPadding),
            modifier = Modifier.padding(DefaultPadding)
        ) {
            // Title
            Text(
                text = stringResource(R.string.find_forecast),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            // Inputs
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MediumPadding),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = lat,
                    onValueChange = { newText ->
                        if (newText.isEmpty() || newText.matches(numberRegex)) {
                            lat = newText
                        }
                    },
                    isError = lat.toDoubleOrNull()?.let { it !in -90.0..90.0 } == true,
                    modifier = Modifier.weight(1f),
                    label = { Text(text = stringResource(R.string.latitude)) },
                    placeholder = { Text("e.g. 37.38") },
                    leadingIcon = { Icon(Icons.Rounded.Explore, contentDescription = null) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )
                )

                OutlinedTextField(
                    value = lon,
                    onValueChange = { newText ->
                        if (newText.isEmpty() || newText.matches(numberRegex)) {
                            lon = newText
                        }
                    },
                    modifier = Modifier.weight(1f),
                    label = { Text(text = stringResource(R.string.longitude)) },
                    placeholder = { Text("e.g. -12.08") },
                    leadingIcon = { Icon(Icons.Rounded.Public, contentDescription = null) },
                    singleLine = true,
                    isError = lon.toDoubleOrNull()?.let { it !in -180.0..180.0 } == true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (isSearchEnabled) onSearch(lat.trim(), lon.trim())
                        }
                    )
                )
            }

            // Actions
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AssistChip(
                    onClick = onUseMyLocation,
                    label = { Text(stringResource(R.string.use_my_location)) },
                    leadingIcon = { Icon(Icons.Rounded.Explore, contentDescription = null) }
                )

                FilledTonalButton(
                    onClick = { onSearch(lat.trim(), lon.trim()) },
                    enabled = isSearchEnabled
                ) {
                    Icon(Icons.Rounded.Search, contentDescription = null)
                    Spacer(Modifier.width(SmallPadding))
                    Text(text = stringResource(R.string.get_forecast))
                }
            }
        }
    }
}


@Composable
private fun DailyForecastCard(
    dailyForecast: DailyForecast
) {
    ElevatedCard(
        shape = RoundedCornerShape(DefaultPadding),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = DefaultPadding)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(DefaultPadding)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.weight(1f)
            ) {
                // Date / day
                Text(dailyForecast.date, style = MaterialTheme.typography.titleSmall)

                // Secondary metrics
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = "Humidity: ${dailyForecast.humidity}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Chance of precipitation: ${dailyForecast.precipitationProbability}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // Temps + icon
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        dailyForecast.highTemperature,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.width(SmallPadding))
                    AsyncImage(
                        model = dailyForecast.weatherIconUrl,
                        contentDescription = null,
                        modifier = Modifier.size(WeatherIconSize)
                    )
                }
                Text(
                    text = dailyForecast.lowTemperature,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

private fun openAppSettings(context: Context) {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", context.packageName, null)
    )
    context.startActivity(intent)
}

@Composable
private fun ErrorView(
    exception: Throwable?,
    retry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(SmallPadding, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
    ) {
        Text(text = stringResource(exception.errorMessageResId), textAlign = TextAlign.Center)

        Button(
            onClick = retry,
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
            )
        ) {
            val buttonText = when (exception) {
                is LocationDeniedPermanentlyException -> stringResource(R.string.open_settings)
                is LocationDeniedException -> stringResource(R.string.request_permission)
                else -> stringResource(R.string.retry)
            }
            Text(text = buttonText)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchComponentPreview(modifier: Modifier = Modifier) {
    WeatherTheme {
        SearchComponent(
            onSearch = { lat, lon ->
            },
            onUseMyLocation = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview(modifier: Modifier = Modifier) {
    val weather = Weather(
        city = "Los Angeles",
        feelsLike = "74°",
        temperature = "23°",
        weatherDescription = "clear",
        weatherIconUrl = "",
        highTemperature = "76°",
        lowTemperature = "71°",
        windSpeed = "1.99 mph",
        windDirection = "SW",
        dailyForecastList = listOf(),
        coordinates = LocationCoordinates(
            latitude = 37.38,
            longitude = -12.08
        )
    )
    WeatherTheme {
        WeatherHeader(weather)
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    val weather = Weather(
        city = "Los Angeles",
        feelsLike = "74°",
        temperature = "23°",
        weatherDescription = "clear",
        weatherIconUrl = "",
        highTemperature = "76°",
        lowTemperature = "71°",
        windSpeed = "1.99 mph",
        windDirection = "SW",
        dailyForecastList = listOf(
            DailyForecast("Today", "76°", "71°", "", "", "23%", "34%"),
            DailyForecast("Tomorrow", "76°", "71°", "", "", "45%", "30%"),
            DailyForecast("Friday", "76°", "71°", "", "", "36%", "12%"),
        ),
        coordinates = LocationCoordinates(
            latitude = 37.38,
            longitude = -12.08
        )
    )

    WeatherTheme {
        HomeScreen(
            uiState = HomeUiState.Success(weather),
            requestPermission = {},
            getWeather = {}
        )
    }
}
