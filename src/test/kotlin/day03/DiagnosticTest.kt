package day03

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class DiagnosticTest {

    private val diagnostic = Diagnostic()

    @Test
    internal fun `calculates most common single bit`() {
        val inputs = listOf("1", "1", "0")
        val actual = diagnostic.calculatePowerConsumption(inputs)
        assertEquals(1, actual.toInt())
    }

    @Test
    internal fun `calculates most common multiple bits`() {
        val inputs = listOf("10000", "10100", "01100")
        val actual = diagnostic.calculatePowerConsumption(inputs)
        assertEquals(20, actual.toInt())
    }

    @Test
    internal fun `verifies flip`() {
        val inputs = listOf("01000")
        val initial = diagnostic.calculatePowerConsumption(inputs)

        val flipped = initial.flip()
        assertEquals(23, flipped.toInt())
    }

    @Test
    internal fun `calculates oxygen`() {
        val inputs = listOf(
            "00100",
            "11110",
            "10110",
            "10111",
            "10101",
            "01111",
            "00111",
            "11100",
            "10000",
            "11001",
            "00010",
            "01010"
        )
        val actual = diagnostic.calculateOxygen(inputs)
        assertEquals(23, actual)
    }

    @Test
    internal fun `calculates carbon dioxide`() {
        val inputs = listOf(
            "00100",
            "11110",
            "10110",
            "10111",
            "10101",
            "01111",
            "00111",
            "11100",
            "10000",
            "11001",
            "00010",
            "01010"
        )
        val actual = diagnostic.calculateCarbonDioxide(inputs)
        assertEquals(10, actual)
    }
}
