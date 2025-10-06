package com.weather.core.common

import kotlin.math.roundToInt

fun Int.degreeToDirection(): String {
    val directions = listOf("N", "NE", "E", "SE", "S", "SW", "W", "NW")
    val index = ((this / 45) + 0.5).toInt() % 8
    return directions[index]
}

fun Double.withDegreeSymbol() = "${this.roundToInt()}°"

fun Double.withDegreeAndFahrenheitSymbol() = "${this.roundToInt()}° F"

fun Double.withMphPostfix() = "$this mph"

fun String.iconNameToUrl() = "https://openweathermap.org/img/wn/$this@2x.png"

