import java.io.File

fun main() {
    d3b()
}

fun d3a() {
    val file = File(ClassLoader.getSystemResource("D3").path)
    var sum = 0
    file.forEachLine {
        val arr = it.toCharArray()
        var i = 0
        loop@ while (i < arr.size / 2) {
            var j = arr.size / 2
            while (j < arr.size) {
                if (arr[i] == arr[j]) {
                    println(arr[i] + " " + arr[i].toCode())
                    sum += arr[i].toCode()
                    break@loop
                }
                j++
            }
            i++
        }
    }
    println(sum)
}

fun d3b() {
    val file = File(ClassLoader.getSystemResource("D3").path)
    var lineNo = 0
    val group = Array(3) { CharArray(0) }
    var sum = 0
    file.forEachLine lit@{
        val position = lineNo % 3
        lineNo++
        group[position] = it.toCharArray()
        if (position == 2) {
            group[0].forEach { first ->
                group[1].forEach { second ->
                    if (first == second) {
                        group[2].forEach { third ->
                            if (third == second) {
                                println("$third ${third.toCode()}")
                                sum += third.toCode()
                                return@lit
                            }
                        }
                    }
                }
            }
        }
    }
    println(sum)
}

private fun Char.toCode(): Int =
    if (this.isUpperCase()) {
        this.code - 64 + 26
    } else {
        this.code - 96
    }
