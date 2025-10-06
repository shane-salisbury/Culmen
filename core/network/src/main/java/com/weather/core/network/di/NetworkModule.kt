package com.weather.core.network.di

import com.weather.core.network.BuildConfig
import com.weather.core.network.WeatherService
import com.weather.core.network.interceptor.AddAppIdInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesJson(): Json = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
        }

    @Provides
    @Singleton
    fun providesAddAppIdInterceptor(): AddAppIdInterceptor = AddAppIdInterceptor()

    @Provides
    @Singleton
    fun providesOkHttpCallFactory(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        addAppIdInterceptor: AddAppIdInterceptor
    ): Call.Factory =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(addAppIdInterceptor)
            .build()

    @Provides
    @Singleton
    fun providesRetrofit(
        json: Json,
        okhttpCallFactory: dagger.Lazy<Call.Factory>,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_WEATHER_URL)
        .callFactory { okhttpCallFactory.get().newCall(it) }
        .addConverterFactory(
            json.asConverterFactory("application/json; charset=UTF8".toMediaType()),
        )
        .build()

    @Provides
    @Singleton
    fun providesWeatherService(retrofit: Retrofit): WeatherService =
        retrofit.create(WeatherService::class.java)
}

private const val BASE_WEATHER_URL = "https://api.openweathermap.org/data/2.5/"
