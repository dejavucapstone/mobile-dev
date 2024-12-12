package com.satria.gymer

import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 */
class ExampleUnitTest {
    @Test
    fun testAddition() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun validateWorkoutTime() {
        // Example test for validating workout time
        val workoutDuration =
            calculateWorkoutDuration(30, 15) // 30 minutes workout, 15 minutes rest
        assertEquals(45, workoutDuration)
    }

    // Utility function for workout duration calculation
    private fun calculateWorkoutDuration(workout: Int, rest: Int): Int {
        return workout + rest
    }

    @Test
    fun checkAppName() {
        val appName = "Gymer"
        assertNotNull(appName)
        assertEquals("Gymer", appName)
    }
}