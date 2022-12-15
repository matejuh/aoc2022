import java.io.File
import kotlin.math.abs

fun main() {
    d15a()
}

private interface Positioned {
    val x: Int
    val y: Int
    val sign: String
}
private data class Sensor(override val x: Int, override val y: Int): Positioned {
    override val sign: String
        get() = "S"
}
private data class Beacon(override val x: Int, override val y: Int): Positioned {
    override val sign: String
        get() = "B"
}

private data class Signal(override val x: Int, override val y: Int): Positioned {
    override val sign: String
        get() = "#"
}

private class Tunnels(firstPoint: Positioned) {
    private val underground: MutableMap<Pair<Int, Int>, Positioned> = mutableMapOf()
    var minX: Int = firstPoint.x
    var maxX: Int = firstPoint.x
    var minY: Int = firstPoint.y
    var maxY: Int = firstPoint.y

    fun add(value: Positioned) {
        val x = value.x
        val y = value.y
        underground.putIfAbsent(Pair(x, y), value)
        if (x < minX) {
            minX = x
        }
        if (x > maxX) {
            maxX = x
        }
        if (y < minY) {
            minY = y
        }
        if (y > maxY) {
            maxY = y
        }
    }

    fun print() {
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                print(underground[Pair(x, y)]?.sign ?: ".")
            }
            println()
        }
    }

    fun withoutBeacon(yPosition: Int): Int {
        var counter = 0
        for (x in minX .. maxX) {
            if (underground[Pair(x, yPosition)] is Signal) {
                counter ++
            }
        }
        return counter
    }
}

private fun d15a() {
    lateinit var tunnels: Tunnels
    val regex = "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)".toRegex()
    File(ClassLoader.getSystemResource("D15test").path).readLines().mapIndexed { idx, line ->
        val (sensorX, sensorY, beaconX, beaconY) = regex.matchEntire(line)!!.destructured
        val sensor = Sensor(sensorX.toInt(), sensorY.toInt())
        val beacon = Beacon(beaconX.toInt(), beaconY.toInt())
        if (idx == 0) {
            tunnels = Tunnels(sensor)
        }
        tunnels.add(sensor)
        tunnels.add(beacon)
        calculateCoverage(tunnels, sensor, beacon)
    }
//    tunnels.print()
    println(tunnels.withoutBeacon(10))
}

private fun calculateCoverage(tunnels: Tunnels, sensor: Sensor, beacon: Beacon) {
    val diffX = abs(sensor.x - beacon.x)
    val diffY = abs( sensor.y - beacon.y)
    val distance = diffX + diffY

    var it = 0
    for (y in sensor.y .. sensor.y + distance) {
        for (x in sensor.x..sensor.x + distance - it) {
            tunnels.add(Signal(x, y))
        }
        it ++
    }
    it = 0
    for (y in sensor.y .. sensor.y + distance) {
        for (x in sensor.x downTo sensor.x - distance + it) {
            tunnels.add(Signal(x, y))
        }
        it ++
    }
    it = 0
    for (y in sensor.y downTo  sensor.y - distance) {
        for (x in sensor.x downTo sensor.x - distance + it) {
            tunnels.add(Signal(x, y))
        }
        it ++
    }
    it = 0
    for (y in sensor.y downTo  sensor.y - distance) {
        for (x in sensor.x .. sensor.x + distance - it) {
            tunnels.add(Signal(x, y))
        }
        it ++
    }
}
