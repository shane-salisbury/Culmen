package com.weather.core.common

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockkObject
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import org.junit.Test

class DateTimeUtilsTest {

    @Test
    fun getEndOfTodayInstant_shouldReturnEndOfTodayInstant() {
        // Given
        mockkObject(TimeZone)
        val timeZone = TimeZone.UTC
        val todayLocalDate = Clock.System.todayIn(timeZone)
        val expectedResult = Instant.parse("${todayLocalDate}T23:59:00Z")

        every { TimeZone.currentSystemDefault() } returns timeZone

        // When
        val endOfTodayInstant = getEndOfTodayInstant()

        // Then
        assertThat(endOfTodayInstant).isEqualTo(expectedResult)
    }

    @Test
    fun epochSecondToInstant_shouldReturnInstant() {
        // Given
        val epochSeconds = 1729533600L
        val expectedResult = Instant.parse("2024-10-21T18:00:00Z")

        // When
        val instant = epochSeconds.epochSecondToInstant()

        // Then
        assertThat(instant).isEqualTo(expectedResult)
    }

    @Test
    fun epochSecondToLocalDateTime_shouldReturnLocalDateTime() {
        // Given
        mockkObject(TimeZone)
        val epochSeconds = 1729533600L
        val expectedResult = LocalDateTime(
            LocalDate(2024,10,21), LocalTime(18, 0))

        every { TimeZone.currentSystemDefault() } returns TimeZone.UTC

        // When
        val localDateTime = epochSeconds.epochSecondToLocalDateTime()

        // Then
        assertThat(localDateTime).isEqualTo(expectedResult)
    }

    @Test
    fun epochSecondToLocalDate_shouldReturnLocalDate() {
        // Given
        mockkObject(TimeZone)
        val epochSeconds = 1729533600L
        val expectedResult = LocalDate(2024,10,21)

        every { TimeZone.currentSystemDefault() } returns TimeZone.UTC

        // When
        val localDate = epochSeconds.epochSecondToLocalDate()

        // Then
        assertThat(localDate).isEqualTo(expectedResult)
    }

    @Test
    fun epochSecondToAmPmTime_shouldReturnAmPmTime() {
        // Given
        mockkObject(TimeZone)
        val epochSeconds = 1729533600L
        val expectedResult = "6:00 PM"

        every { TimeZone.currentSystemDefault() } returns TimeZone.UTC

        // When
        val amPmTime = epochSeconds.epochSecondToAmPmTime()

        // Then
        assertThat(amPmTime).isEqualTo(expectedResult)
    }

    @Test
    fun toDateForDailyForecast_shouldReturnDateForDailyForecast() {
        // Given
        mockkObject(TimeZone)
        val localDate = LocalDate(2024,10,21)
        val expectedResult = "Monday, October 21"

        every { TimeZone.currentSystemDefault() } returns TimeZone.UTC

        // When
        val dateForDailyForecast = localDate.toDateForDailyForecast()

        // Then
        assertThat(dateForDailyForecast).isEqualTo(expectedResult)
    }
}