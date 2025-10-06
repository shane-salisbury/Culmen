package com.weather.core.common

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class WeatherUtilsTest {
    @Test
    fun degreeToDirection_shouldReturnCorrectDirection() {
        // Given
        val degrees = 45
        val expectedResult = "NE"

        // When
        val direction = degrees.degreeToDirection()

        // Then
        assertThat(expectedResult).isEqualTo(direction)
    }

    @Test
    fun withDegreeSymbol_shouldReturnCorrectString() {
        // Given
        val degrees = 25.3
        val expectedResult = "25°"

        // When
        val result = degrees.withDegreeSymbol()

        // Then
        assertThat(expectedResult).isEqualTo(result)
    }

    @Test
    fun withDegreeAndFahrenheitSymbol_shouldReturnCorrectString() {
        // Given
        val degrees = 45.4
        val expectedResult = "45° F"

        // When
        val result = degrees.withDegreeAndFahrenheitSymbol()

        // Then
        assertThat(expectedResult).isEqualTo(result)
    }

    @Test
    fun withMphPostfix_shouldReturnCorrectString() {
        // Given
        val mph = 15.6
        val expectedResult = "15.6 mph"

        // When
        val result = mph.withMphPostfix()

        // Then
        assertThat(expectedResult).isEqualTo(result)
    }

    @Test
    fun iconNameToUrl_shouldReturnCorrectUrl() {
        // Given
        val iconName = "ic1"
        val expectedResult = "https://openweathermap.org/img/wn/ic1@2x.png"

        // When
        val result = iconName.iconNameToUrl()

        // Then
        assertThat(expectedResult).isEqualTo(result)
    }
}