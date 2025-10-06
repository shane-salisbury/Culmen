package com.weather.core.location.di

import com.weather.core.location.LocationProvider
import com.weather.core.location.LocationProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LocationModule {

    @Binds
    @Singleton
    fun bindLocationProvider(
        locationProviderImpl: LocationProviderImpl
    ): LocationProvider
}