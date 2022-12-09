import java.io.File

fun main() {
    d9a()
}

val knots = List(10) { Knot() }
val tailPositions = HashSet<Position>().apply {
    add(Position(0, 0))
}

private fun d9a() {
    File(ClassLoader.getSystemResource("D9").path).forEachLine { line ->
        val (direction, lengthStr) = line.split(" ")
        val length = lengthStr.toInt()
        when (direction) {
            "U" -> move(0, length)
            "L" -> move(-length, 0)
            "D" -> move(0, -length)
            "R" -> move(length, 0)
        }
    }
    println(tailPositions.size)
}

private fun touching(first: Knot, second: Knot): Boolean = (first.x in second.x - 1..second.x + 1) && (first.y in second.y - 1..second.y + 1)

private fun fixTailPosition() {
    for (i in 1 until knots.size) {
        val head = knots[i-1]
        val tail = knots[i]
        if (!touching(head, tail)) {
            // vertical move
            if (head.x == tail.x) {
                if (head.y > tail.y) {
                    tail.y++
                } else {
                    tail.y--
                }
                // horizontal move
            } else if (head.y == tail.y) {
                if (head.x > tail.x) {
                    tail.x++
                } else {
                    tail.x--
                }
            } else {
                if (head.y > tail.y) {
                    if (head.x > tail.x) {
                        tail.x++
                        tail.y++
                    } else {
                        tail.x--
                        tail.y++
                    }
                } else {
                    if (head.x > tail.x) {
                        tail.x++
                        tail.y--
                    } else {
                        tail.x--
                        tail.y--
                    }
                }
            }
            if (i == knots.size - 1) {
                tailPositions.add(Position(tail.x, tail.y))
            }
        }
    }
}

private fun move(x: Int, y: Int) {
    if (x > 0) {
        for (i in 1..x) {
            knots.first().x++
            fixTailPosition()
        }
    } else {
        for (i in -1 downTo x) {
            knots.first().x--
            fixTailPosition()
        }
    }

    if (y > 0) {
        for (i in 1..y) {
            knots.first().y++
            fixTailPosition()
        }
    } else {
        for (i in -1 downTo y) {
            knots.first().y--
            fixTailPosition()
        }
    }
    knots.forEachIndexed { i, knot -> print("$i: $knot" ) }
    println()
}

data class Knot(var x: Int = 0, var y: Int = 0)

data class Position(val x: Int, val y: Int)
