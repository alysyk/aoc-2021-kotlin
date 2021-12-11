package day04

import java.nio.file.Paths

class BingoCard(values: List<Int>) {
    private val numberToCell: MutableMap<Int, Cell> = mutableMapOf()
    private val rows: MutableMap<Int, Block> = mutableMapOf()
    private val columns: MutableMap<Int, Block> = mutableMapOf()

    init {
        require(values.size == 25)
        values.forEachIndexed { idx, value ->
            val cell = Cell(value)
            numberToCell[value] = cell

            val column = columns.getOrPut(idx % 5) { Block() }
            column.cells.add(cell)

            val row = rows.getOrPut(idx / 5) { Block() }
            row.cells.add(cell)
        }
    }

    private class Cell(val value: Int) {
        var marked: Boolean = false
    }

    private class Block {
        val cells: MutableList<Cell> = mutableListOf()

        fun isWinner(): Boolean {
            return cells.all { it.marked }
        }
    }

    fun markCell(value: Int) {
        numberToCell[value]?.marked = true
    }

    fun isWinner(): Boolean {
        return (columns.values + rows.values)
            .any { it.isWinner() }
    }

    fun getUnmarkedSum(): Int {
        return numberToCell.values
            .filterNot { it.marked }
            .sumOf { it.value }
    }
}

class BingoGame(inputs: List<String>) {

    private val validCardLine = "(\\d+\\s+){4}(\\d+)".toRegex()

    private val calledNumbers: List<Int>
    private val cards: MutableList<BingoCard> = mutableListOf()

    init {
        calledNumbers = inputs.first().split(",").map { it.toInt() }
        val remainingInputs = inputs.drop(1)
            .filter { validCardLine.containsMatchIn(it) }
        val currentCard = mutableListOf<Int>()
        remainingInputs.forEach { input ->
            val cardLine = input.trim()
                .replace("\\s+".toRegex(), " ")
                .split(" ")
                .map { it.toInt() }
            currentCard.addAll(cardLine)

            if (currentCard.size == 25) {
                cards.add(BingoCard(currentCard))
                currentCard.clear()
            }
        }
    }

    fun getFirstWinner(): Int {
        calledNumbers.forEach { calledNumber ->
            cards.forEach { it.markCell(calledNumber) }

            val winningCard = cards.find { it.isWinner() }
            if (winningCard != null) return winningCard.getUnmarkedSum() * calledNumber
        }
        throw Exception("No winning cards")
    }

    fun getLastWinner(): Int {
        calledNumbers.forEach { calledNumber ->
            cards.forEach { it.markCell(calledNumber) }

            if (cards.size == 1 && cards.first().isWinner()) {
                return cards.first().getUnmarkedSum() * calledNumber
            }

            cards.removeAll(cards.filter { it.isWinner() })
        }
        throw Exception("Last card does not win")
    }
}


fun main() {
    val path = Paths.get("day04", "input.txt").toString()
    val lines = BingoCard::class.java.classLoader.getResource(path)!!.readText().lines()

    val game = BingoGame(lines)

    println("First winning sum: ${game.getFirstWinner()}")
    println("Last winning sum: ${game.getLastWinner()}")
}
