package day04

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BingoCardTest {
    @Test
    internal fun `cell does not exist - no change`() {
        val values = IntRange(0, 24).toList()
        val card = BingoCard(values)

        card.markCell(25)

        assertFalse(card.isWinner())
        assertEquals(values.sum(), card.getUnmarkedSum())
    }

    @Test
    internal fun `marks a single cell - not a winner`() {
        val values = IntRange(0, 24).toList()
        val card = BingoCard(values)

        card.markCell(24)

        assertFalse(card.isWinner())
        assertEquals(values.sum() - 24, card.getUnmarkedSum())
    }

    @Test
    internal fun `marks a column - winner`() {
        val values = IntRange(0, 24).toList()
        val card = BingoCard(values)

        val calledValues = listOf(0, 1, 2, 3, 4)
        calledValues
            .forEach { card.markCell(it) }

        assertTrue(card.isWinner())
        assertEquals(values.sum() - calledValues.sum(), card.getUnmarkedSum())
    }

    @Test
    internal fun `marks a row - winner`() {
        val values = IntRange(0, 24).toList()
        val card = BingoCard(values)

        val calledValues = listOf(0, 5, 10, 15, 20)
        calledValues
            .forEach { card.markCell(it) }

        assertTrue(card.isWinner())
        assertEquals(values.sum() - calledValues.sum(), card.getUnmarkedSum())
    }
}
