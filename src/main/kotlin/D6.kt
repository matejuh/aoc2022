import java.io.File

fun main() {
    d6b()
}

private fun d6a() {
    run(4)
}

private fun d6b() {
    run(14)
}

private fun run(capacity: Int) {
    val arrayDeque = ArrayDeque<Char>(14)
    var i = 0
    val reader = File("/Users/matejplch/Programming/aoc2022/src/main/resources/D6").reader()
    loop@ do {
        val r = reader.read()
        i++
        arrayDeque.put(r.toChar(), capacity)
        if (!arrayDeque.hasDuplicates(capacity)) {
            println(i)
            break@loop
        }
    } while (r != -1)
}

private fun <T> ArrayDeque<T>.put(item: T, capacity: Int) {
    if (size == capacity) {
        removeFirst()
    }
    addLast(item)
}

private fun <T> ArrayDeque<T>.hasDuplicates(capacity: Int): Boolean {
    if (size < capacity) return true
    var i = 0
    while (i < size - 1) {
        val c1 = get(i)
        var j = i + 1
        while (j < size) {
            val c2 = get(j)
            if (c1 == c2) {
                return true
            }
            j++
        }
        i++
    }
    return false
}

