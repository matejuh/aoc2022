import java.io.File

fun main() {
    d4b()
}

private fun d4a() {
    var sum = 0
    val regex = "(\\d+)-(\\d+),(\\d+)-(\\d+)".toRegex()
    val file = File(ClassLoader.getSystemResource("D4").path)
    file.forEachLine {
        // 2-6,4-8
        val result = regex.matchEntire(it)!!
        val first = Range(result.groupValues[1].toInt(), result.groupValues[2].toInt())
        val second = Range(result.groupValues[3].toInt(), result.groupValues[4].toInt())
        val fullyContains = if (first.size() > second.size()) {
            first.fullyContains(second)
        } else {
            second.fullyContains(first)
        }
        if (fullyContains) sum++
    }
    println(sum)
}

private fun d4b() {
    var sum = 0
    val regex = "(\\d+)-(\\d+),(\\d+)-(\\d+)".toRegex()
    val file = File(ClassLoader.getSystemResource("D4").path)
    file.forEachLine {
        // 2-6,4-8
        val result = regex.matchEntire(it)!!
        val first = Range(result.groupValues[1].toInt(), result.groupValues[2].toInt())
        val second = Range(result.groupValues[3].toInt(), result.groupValues[4].toInt())
        val overlap = if (first.size() > second.size()) {
            first.overlap(second)
        } else {
            second.overlap(first)
        }
        if (overlap) { sum++ }
    }
    println(sum)
}

data class Range(val start: Int, val endInclusive: Int) {
    fun size(): Int = endInclusive - start

    fun fullyContains(range: Range): Boolean = (this.start <= range.start) && (this.endInclusive >= range.endInclusive)

    fun overlap(range: Range): Boolean = contains(range.start) || contains(range.endInclusive)

    private fun contains(value: Int): Boolean = value in start..endInclusive
}
