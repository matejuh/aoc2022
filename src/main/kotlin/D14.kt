import java.io.File
import kotlin.math.max
import kotlin.math.min

fun main() {
    d14a()
}

const val ROCK = "#"
const val SAND = "o"
const val SPACE = "."

private fun d14a() {
    val underground = Underground()
    File(ClassLoader.getSystemResource("D14").path).readLines().map { line ->
        line.split(" -> ")
            .map {
                val (x, y) = it.split(",")
                Pair(x.toInt(), y.toInt())
            }.windowed(2, 1)
            .forEach {
                val (start, end) = it
                if (start.first == end.first) {
                    val x = start.first
                    for (y in min(start.second, end.second)..max(start.second, end.second)) {
                        underground.add(x, y, ROCK)
                    }
                } else {
                    val y = start.second
                    for (x in min(start.first, end.first)..max(start.first, end.first)) {
                        underground.add(x, y, ROCK)
                    }
                }
            }
    }

    var it = 0
    do {
        var flowingOut = false
        it++
        var x = 500
        var y = 0
        var collission = false
        do {
            if (underground.overflow(y)) {
                println("Overflow: ${it - 1}")
                flowingOut = true
            } else if (underground.canMove(x, y + 1)) {
                y += 1
            } else if (underground.canMove(x - 1, y + 1)) {
                y += 1
                x -= 1
            } else if (underground.canMove(x + 1, y + 1)) {
                y += 1
                x += 1
            } else {
                underground.add(x, y, SAND)
                collission = true
            }
        } while (!collission && !flowingOut)
    } while (!flowingOut)
    underground.print()
}

private class Underground {
    private val underground: MutableMap<Pair<Int, Int>, String> = mutableMapOf()
    var minX: Int = 500
    var maxX: Int = 500
    var maxY: Int = 0

    fun add(x: Int, y: Int, value: String) {
        underground[Pair(x, y)] = value
        if (x < minX) {
            minX = x
        }
        if (x > maxX) {
            maxX = x
        }
        if (y > maxY) {
            maxY = y
        }
    }

    fun overflow(y: Int): Boolean = maxY != 0 && y > maxY

    fun canMove(x: Int, y: Int): Boolean = !underground.containsKey(Pair(x, y))

    fun print() {
        for (y in 0..maxY) {
            for (x in minX..maxX) {
                print(underground.getOrDefault(Pair(x, y), SPACE))
            }
            println()
        }
    }
}
