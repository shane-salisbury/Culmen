package com.weather.core.common

import android.util.Log
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CoroutinesTest {
    @Test
    fun suspendRunCatching_shouldReturnSuccess_whenBlockExecutesSuccessfully() = runTest {
        // When
        val result = suspendRunCatching { 1 }

        // Then
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(1)
    }

    @Test
    fun suspendRunCatching_shouldReturnFailure_whenBlockThrowsException() = runTest {
        // Given
        val exception = Exception("Some exception")
        mockkStatic(Log::class)

        every { Log.i(any(), any(), any()) } returns 0

        // When
        val result = suspendRunCatching { throw exception }

        // Then
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isEqualTo(exception)
    }
}