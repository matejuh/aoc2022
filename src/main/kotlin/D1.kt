import java.io.File
import java.util.PriorityQueue

fun main() {
    d1b()
}

fun d1a() {
    var max = 0
    var n = 0
    val file = File(ClassLoader.getSystemResource("D1").path)
    file.forEachLine {
        if (it.isNotBlank()) {
            n += it.toInt()
        } else {
            println(n)
            if (n > max) {
                max = n
            }
            n = 0
        }
    }
    println("MAX: $max")
}

fun d1b() {
    val priorityQueue = PriorityQueue<Int>(4) { a, b -> if (a > b) 1 else -1 }
    val file = File(ClassLoader.getSystemResource("D1").path)
    var n = 0
    file.forEachLine {
        if (it.isNotBlank()) {
            n += it.toInt()
        } else {
            priorityQueue.add(n)
            if (priorityQueue.size > 3) {
                priorityQueue.poll()
            }
            n = 0
        }
    }
    var total = 0
    while(priorityQueue.isNotEmpty()) {
        total += priorityQueue.poll()
    }
    println("Total: $total")
}
