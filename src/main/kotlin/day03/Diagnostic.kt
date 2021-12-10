package day03

import java.nio.file.Paths

data class PowerConsumption(val mostCommon: BooleanArray) {

    fun toInt(): Int {
        var result = 0

        for (i in mostCommon.size - 1 downTo 0) {
            result = result.rotateLeft(1)
            if (mostCommon[i]) result += 1
        }
        return result
    }

    fun flip(): PowerConsumption {
        return PowerConsumption(BooleanArray(mostCommon.size) { !mostCommon[it] })
    }
}

class Diagnostic {

    private val validRegex = Regex("(\\d+)")

    private fun getValidInputs(inputs: List<String>): List<String> {
        return inputs.mapNotNull { validRegex.find(it)?.value }
    }

    fun calculatePowerConsumption(inputs: List<String>): PowerConsumption {
        val validInputs = getValidInputs(inputs)
        val maxBits = validInputs.maxOf { it.length }

        val mostCommon = BooleanArray(maxBits)
        for (i in 0 until maxBits) {
            val (zeros, ones) = validInputs.map { it[i].digitToInt() }
                .partition { it == 0 }
            if (ones.size > zeros.size) mostCommon[maxBits - 1 - i] = true
        }

        return PowerConsumption(mostCommon)
    }


    fun calculateOxygen(inputs: List<String>): Int {
        var remainingInputs = getValidInputs(inputs)
        val maxBits = remainingInputs.maxOf { it.length }

        for (i in 0 until maxBits) {
            val (zeros, ones) = remainingInputs.map { it[i].digitToInt() }
                .partition { it == 0 }

            remainingInputs = if (zeros.size > ones.size) {
                remainingInputs.filter { it[i] == '0' }
            } else {
                remainingInputs.filter { it[i] == '1' }
            }

            if (remainingInputs.size == 1) break
        }

        return remainingInputs.first().toInt(2)
    }

    fun calculateCarbonDioxide(inputs: List<String>): Int {
        var remainingInputs = getValidInputs(inputs)
        val maxBits = remainingInputs.maxOf { it.length }

        for (i in 0 until maxBits) {
            val (zeros, ones) = remainingInputs.map { it[i].digitToInt() }
                .partition { it == 0 }

            remainingInputs = if (zeros.size > ones.size) {
                remainingInputs.filter { it[i] == '1' }
            } else {
                remainingInputs.filter { it[i] == '0' }
            }

            if (remainingInputs.size == 1) break
        }

        return remainingInputs.first().toInt(2)

    }
}

fun main() {
    val path = Paths.get("day03", "input.txt").toString()
    val lines = Diagnostic::class.java.classLoader.getResource(path)!!.readText().lines()

    val result = Diagnostic().calculatePowerConsumption(lines)
    println("Power Consumption: ${result.toInt() * result.flip().toInt()}")

    val oxygen = Diagnostic().calculateOxygen(lines)
    val co2 = Diagnostic().calculateCarbonDioxide(lines)

    println("Life Support: ${oxygen * co2}")
}
