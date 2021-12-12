package day05

import java.nio.file.Paths

class Hydrothermals(text: List<String>) {

    private val validLine = "(\\d+),(\\d+)\\s+->\\s+(\\d+),(\\d+)".toRegex()

    val lines: List<Line>

    init {
        lines = text.filter { validLine.containsMatchIn(it) }
            .map {
                val (x1, y1, x2, y2) = validLine.find(it)!!.destructured
                Line(x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt())
            }
    }

    fun getHorizontalAndVertical(): Coordinates {
        return getCoordinates(lines.filterNot { it.getDirection() == Direction.DIAGONAL })
    }

    fun getAllDirections(): Coordinates {
        return getCoordinates(lines)
    }

    fun getCoordinates(validLines: List<Line>): Coordinates {
        val coords = Coordinates()

        validLines.forEach {
            when (it.getDirection()) {
                Direction.HORIZONTAL -> {
                    getRange(it.y1, it.y2)
                        .forEach { y -> coords.addCoordinate(it.x1, y) }
                }
                Direction.VERTICAL -> {
                    getRange(it.x1, it.x2)
                        .forEach { x -> coords.addCoordinate(x, it.y1) }
                }
                Direction.DIAGONAL -> {
                    getRange(it.x1, it.x2).zip(getRange(it.y1, it.y2))
                        .forEach { (x, y) -> coords.addCoordinate(x, y) }
                }
            }
        }

        return coords
    }

    private fun getRange(first: Int, second: Int): IntProgression {
        return if (first < second) (first..second) else (first downTo second)
    }
}

enum class Direction {
    HORIZONTAL,
    VERTICAL,
    DIAGONAL
}

data class Line(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
    fun getDirection(): Direction {
        if (x1 == x2) return Direction.HORIZONTAL
        if (y1 == y2) return Direction.VERTICAL
        return Direction.DIAGONAL
    }
}

class Coordinates {
    val xyCount: MutableMap<Int, MutableMap<Int, Int>> = mutableMapOf()

    fun addCoordinate(x: Int, y: Int) {
        val yMap = xyCount.getOrDefault(x, mutableMapOf())
        yMap[y] = yMap.getOrDefault(y, 0) + 1
        xyCount[x] = yMap
    }
}

fun main() {
    val path = Paths.get("day05", "input.txt").toString()
    val lines = Hydrothermals::class.java.classLoader.getResource(path)!!.readText().lines()

    val hydrothermals = Hydrothermals(lines)

    val horizontalAndVerticalDupes = hydrothermals.getHorizontalAndVertical().xyCount
        .flatMap { it.value.values }
        .count { it >= 2 }

    println("H+V duplicates: $horizontalAndVerticalDupes")

    val allDirectionDupes = hydrothermals.getAllDirections().xyCount
        .flatMap { it.value.values }
        .count { it >= 2 }

    println("All direction duplicates: $allDirectionDupes")
}
