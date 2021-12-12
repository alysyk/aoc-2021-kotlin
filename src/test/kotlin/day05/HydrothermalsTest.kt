package day05

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class HydrothermalsTest {
    @Test
    internal fun `creates lines from input`() {
        val textLines = listOf("278,160 -> 278,453")
        val hydrothermals = Hydrothermals(textLines)
        assertEquals(hydrothermals.lines, listOf(Line(278, 160, 278, 453)))
    }

    @Test
    internal fun `line is a diagonal`() {
        val line = Line(571, 160, 278, 453)
        assertEquals(Direction.DIAGONAL, line.getDirection())
    }

    @Test
    internal fun `line is horizontal`() {
        val line = Line(278, 160, 278, 453)
        assertEquals(Direction.HORIZONTAL, line.getDirection())
    }

    @Test
    internal fun `line is vertical`() {
        val line = Line(571, 453, 278, 453)
        assertEquals(Direction.VERTICAL, line.getDirection())
    }

    @Test
    internal fun `adds a coordinate`() {
        val coords = Coordinates()
        coords.addCoordinate(1, 1)
        val expected = mapOf(1 to mapOf(1 to 1))
        assertEquals(expected, coords.xyCount)
    }

    @Test
    internal fun `adds a coordinate twice`() {
        val coords = Coordinates()
        coords.addCoordinate(1, 1)
        coords.addCoordinate(1, 1)
        val expected = mapOf(1 to mapOf(1 to 2))
        assertEquals(expected, coords.xyCount)
    }

    @Test
    internal fun `adds two different coordinates with the same x`() {
        val coords = Coordinates()
        coords.addCoordinate(1, 1)
        coords.addCoordinate(1, 4)
        val expected = mapOf(1 to mapOf(1 to 1, 4 to 1))
        assertEquals(expected, coords.xyCount)
    }

    @Test
    internal fun `adds two different coordinates with the same y`() {
        val coords = Coordinates()
        coords.addCoordinate(1, 1)
        coords.addCoordinate(4, 1)
        val expected = mapOf(1 to mapOf(1 to 1), 4 to mapOf(1 to 1))
        assertEquals(expected, coords.xyCount)
    }
}
