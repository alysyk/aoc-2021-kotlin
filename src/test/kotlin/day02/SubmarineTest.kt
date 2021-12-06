package day02

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SubmarineTest {

    private val submarine = Submarine()

    @Test
    internal fun `handles forward action`() {
        val instructions = listOf("forward 10", "forward 20")
        val finalPosition = submarine.findFinalPosition(instructions)
        assertEquals(Position(30, 0), finalPosition)
    }

    @Test
    internal fun `handles down action`() {
        val instructions = listOf("down 10", "down 20")
        val finalPosition = submarine.findFinalPosition(instructions)
        assertEquals(Position(0, 30), finalPosition)
    }

    @Test
    internal fun `handles up action`() {
        val instructions = listOf("up 10", "up 20")
        val finalPosition = submarine.findFinalPosition(instructions)
        assertEquals(Position(0, -30), finalPosition)
    }

    @Test
    internal fun `returns multiplication value`() {
        val instructions = listOf("forward 10", "down 20")
        val multiplicationValue = submarine.findFinalPosition(instructions).getMultiplicationValue()
        assertEquals(200, multiplicationValue)
    }

    @Test
    internal fun `handles forward action (modified)`() {
        val instructions = listOf("forward 10", "forward 20")
        val finalPosition = submarine.findFinalPositionModified(instructions)
        assertEquals(Position(30, 0), finalPosition)
    }

    @Test
    internal fun `handles down aim`() {
        val instructions = listOf("down 2", "forward 3")
        val finalPosition = submarine.findFinalPositionModified(instructions)
        assertEquals(Position(3, 6), finalPosition)
    }
}
