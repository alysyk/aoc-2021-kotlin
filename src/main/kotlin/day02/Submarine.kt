package day02

import java.nio.file.Paths

data class Position(val horizontal: Int, val vertical: Int) {
    fun getMultiplicationValue(): Int {
        return horizontal * vertical
    }
}

class Submarine {

    private val validInstruction = Regex("(forward|up|down) (\\d+)")

    fun findFinalPosition(instructions: List<String>): Position {
        var currentPosition = Position(0, 0)

        instructions.filter { validInstruction.containsMatchIn(it) }
            .forEach {
                val (direction, amount) = validInstruction.find(it)!!.destructured
                when (direction) {
                    "forward" -> currentPosition =
                        Position(
                            currentPosition.horizontal + amount.toInt(),
                            currentPosition.vertical
                        )
                    "up" -> currentPosition =
                        Position(
                            currentPosition.horizontal,
                            currentPosition.vertical - amount.toInt()
                        )
                    "down" -> currentPosition =
                        Position(
                            currentPosition.horizontal,
                            currentPosition.vertical + amount.toInt()
                        )
                }
            }

        return currentPosition
    }

    fun findFinalPositionModified(instructions: List<String>): Position {
        var currentPosition = Position(0, 0)
        var aim = 0

        instructions.filter { validInstruction.containsMatchIn(it) }
            .forEach {
                val (direction, amount) = validInstruction.find(it)!!.destructured
                when (direction) {
                    "up" -> aim -= amount.toInt()
                    "down" -> aim += amount.toInt()
                    "forward" -> {
                        val newHorizontal = currentPosition.horizontal + amount.toInt()

                        @Suppress("KotlinConstantConditions")
                        val newVertical = currentPosition.vertical + (aim * amount.toInt())

                        currentPosition = Position(newHorizontal, newVertical)
                    }
                }
            }

        return currentPosition
    }
}

fun main() {
    val path = Paths.get("day02", "input.txt").toString()
    val lines = Submarine::class.java.classLoader.getResource(path).readText().lines()
    val finalPosition = Submarine().findFinalPosition(lines)
    println(finalPosition.getMultiplicationValue())

    val finalPositionModified = Submarine().findFinalPositionModified(lines)
    println(finalPositionModified.getMultiplicationValue())
}
