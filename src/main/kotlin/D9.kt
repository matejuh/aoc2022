import java.io.File

fun main() {
    d9a()
}

val head = Knot()
val tail = Knot()
val tailPositions = HashSet<Position>().apply {
    add(Position(tail.x, tail.y))
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

private fun touching(): Boolean = (head.x in tail.x - 1..tail.x + 1) && (head.y in tail.y - 1..tail.y + 1)

private fun fixTailPosition() {
    if (!touching()) {
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
                    tail.x ++
                    tail.y ++
                } else {
                    tail.x --
                    tail.y ++
                }
            } else {
                if (head.x > tail.x) {
                    tail.x ++
                    tail.y --
                } else {
                    tail.x --
                    tail.y --
                }
            }
        }
        tailPositions.add(Position(tail.x, tail.y))
    }
}

private fun move(x: Int, y: Int) {
    if (x > 0) {
        for (i in 1..x) {
            head.x++
            fixTailPosition()
        }
    } else {
        for (i in -1 downTo x) {
            head.x--
            fixTailPosition()
        }
    }

    if (y > 0) {
        for (i in 1..y) {
            head.y++
            fixTailPosition()
        }
    } else {
        for (i in -1 downTo y) {
            head.y--
            fixTailPosition()
        }
    }

    println("head: $head tail: $tail")
}

data class Knot(var x: Int = 0, var y: Int = 0)

data class Position(val x: Int, val y: Int)
