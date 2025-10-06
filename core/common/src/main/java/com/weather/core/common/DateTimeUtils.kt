package com.weather.core.common

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn

fun getEndOfTodayInstant(): Instant {
    val todayLocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val endOfDayLocalTime = LocalTime(23, 59)
    val endOfTodayLocalDateTime = LocalDateTime(todayLocalDate, endOfDayLocalTime)
    return endOfTodayLocalDateTime.toInstant(TimeZone.currentSystemDefault())
}

fun Long.epochSecondToInstant(): Instant {
    return Instant.fromEpochSeconds(this)
}

fun Long.epochSecondToLocalDateTime(): LocalDateTime {
    return epochSecondToInstant().toLocalDateTime(TimeZone.currentSystemDefault())
}

fun Long.epochSecondToLocalDate(): LocalDate {
    return epochSecondToLocalDateTime().date
}

fun Long.epochSecondToAmPmTime(): String {
    val localDateTime = epochSecondToLocalDateTime()
    return localDateTime.format(TimeWithAmPmFormat)
}

fun LocalDate.toDateForDailyForecast(): String {
    return format(DateForDailyForecastFormat)
}

private val TimeWithAmPmFormat = LocalDateTime.Format {
    amPmHour(padding = Padding.NONE)
    char(':')
    minute()
    char(' ')
    amPmMarker("AM", "PM")
}

private val DateForDailyForecastFormat = LocalDate.Format {
    dayOfWeek(DayOfWeekNames.ENGLISH_FULL)
    char(',')
    char(' ')
    monthName(MonthNames.ENGLISH_FULL)
    char(' ')
    dayOfMonth()
}
