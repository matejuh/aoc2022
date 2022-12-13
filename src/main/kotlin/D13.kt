import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File

fun main() {
//    d13a()
    d13b()
}

private fun d13a() {
    var sum = 0
    var counter = 0
    File(ClassLoader.getSystemResource("D13").path).useLines {
        val iterator = it.iterator()
        while (iterator.hasNext()) {
            counter++
            val l1 = readLine(iterator.next())
            val l2 = readLine(iterator.next())
            if (rightOrder(l1, l2) == 1) {
                println("$counter right order")
                sum += counter
            } else {
                println("$counter wrong order")
            }
            if (iterator.hasNext()) {
                iterator.next()
            }
        }
    }
    println(sum)
}

private fun d13b() {
    val dividers = listOf(arrayListOf(arrayListOf(2)), arrayListOf(arrayListOf(6)))
    val result = File(ClassLoader.getSystemResource("D13").path).readLines().filter { it.isNotEmpty() }
        .map { readLine(it) }
        .plus(dividers)
        .sortedWith { left, right -> rightOrder(left, right) * -1 }
        .foldIndexed(1) { idx, acc, curr -> if (curr in dividers) { acc * (idx + 1) } else { acc } }
    println(result)

}

private fun readLine(line: String): Any = jacksonObjectMapper().readValue(line, Any::class.java)


private fun rightOrder(left: Any, right: Any): Int {
    if (left is Int && right is Int) {
        return if (left < right) {
            1
        } else if (left > right) {
            -1
        } else {
            0
        }
    }
    if (left is ArrayList<*> && right is ArrayList<*>) {
        var result: Int
        var idx = 0
        while (idx < left.size && idx < right.size) {
            result = rightOrder(left[idx], right[idx])
            if (result == 0) {
                idx++
            } else {
                return result
            }
        }
        return if (idx == left.size && idx == right.size) {
            // next item
            0
        } else if (idx >= left.size) {
            1
        } else {
            -1
        }
    }
    return if (left is Int) {
        rightOrder(arrayListOf(left), right)
    } else {
        rightOrder(left, arrayListOf(right))
    }
}
