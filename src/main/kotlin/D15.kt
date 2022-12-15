import java.io.File
import kotlin.math.abs
import kotlin.math.max

fun main() {
//    d15a()
    d15b()
}

private fun d15a() {
    val regex = "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)".toRegex()
    val goal = 2000000
    val safePoints = mutableSetOf<Int>()
    File(ClassLoader.getSystemResource("D15").path).readLines().forEach { line ->
        val (sensorX, sensorY, beaconX, beaconY) = regex.matchEntire(line)!!.destructured
        val sensor = Position(sensorX.toInt(), sensorY.toInt())
        val beacon = Position(beaconX.toInt(), beaconY.toInt())
        val diffX = abs(sensor.x - beacon.x)
        val diffY = abs( sensor.y - beacon.y)
        val manhattanDistance = diffX + diffY
        val yDistanceToGoal = abs(sensor.y - goal)
        val xDistanceToGoal = manhattanDistance - yDistanceToGoal

        for (covered in sensor.x - xDistanceToGoal .. sensor.x + xDistanceToGoal) {
            if (beacon != Position(covered, goal)) {
                safePoints.add(covered)
            }
        }
    }
    println(safePoints.size)
}

private fun d15b() {
    val min = 0
    val max = 4000000
    val regex = "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)".toRegex()
    val safeRangesPerLine = Array<MutableList<IntRange>>(max + 1) { mutableListOf() }
    File(ClassLoader.getSystemResource("D15").path).readLines().forEach { line ->
        val (sensorX, sensorY, beaconX, beaconY) = regex.matchEntire(line)!!.destructured
        val sensor = Position(sensorX.toInt(), sensorY.toInt())
        val beacon = Position(beaconX.toInt(), beaconY.toInt())
        val diffX = abs(sensor.x - beacon.x)
        val diffY = abs( sensor.y - beacon.y)
        val manhattanDistance = diffX + diffY

        for (y in min .. max) {
            val distanceToY = abs(sensor.y - y)
            val xWidth = manhattanDistance - distanceToY
            if (xWidth > 0) {
                safeRangesPerLine[y].add(sensor.x - xWidth .. sensor.x + xWidth)
            }
        }
    }

    val signal = safeRangesPerLine.mapIndexedNotNull { y, safeRanges ->
        val sorted = safeRanges.sortedBy { it.first }
        var previousRangeEnd = sorted.first().last
        val rangeWithGap = sorted.drop(1).find { range ->
            if (range.first > previousRangeEnd) {
                true
            } else {
                previousRangeEnd = max(previousRangeEnd, range.last)
                false
            }
        }
        if (rangeWithGap == null) {
            null
        } else {
            (rangeWithGap.first - 1) * 4000000L + y
        }
    }.first()
    println(signal)
}
