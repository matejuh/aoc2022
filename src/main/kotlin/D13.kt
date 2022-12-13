import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File

fun main() {
    d13a()
}

private fun d13a() {
    var sum = 0
    var counter = 0
    File(ClassLoader.getSystemResource("D13").path).useLines {
        val iterator = it.iterator()
        while(iterator.hasNext()) {
            counter++
            val l1 = readLine(iterator.next())
            val l2 = readLine(iterator.next())
            if (rightOrder(l1, l2) == true) {
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

private fun readLine(line: String): Any = jacksonObjectMapper().readValue(line, Any::class.java)


private fun rightOrder(left: Any, right: Any): Boolean? {
    if (left is Int && right is Int) {
        return if (left < right) {
            true
        } else if (left > right) {
            false
        } else {
            null
        }
    }
    if (left is ArrayList<*> && right is ArrayList<*>) {
        var result: Boolean?
        var idx = 0
        while(idx < left.size && idx < right.size) {
            result = rightOrder(left[idx], right[idx])
            if (result == null) {
                idx++
            } else {
                return result
            }
        }
        return if (idx == left.size && idx == right.size) {
            // next item
            null
        } else idx >= left.size
    }
    return if (left is Int) {
        rightOrder(arrayListOf(left), right)
    } else {
        rightOrder(left, arrayListOf(right))
    }
}
