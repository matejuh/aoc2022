import java.io.File

fun main() {
    d5b()
}

lateinit var stacks: Array<ArrayDeque<Char>>
val regex = "move (\\d+) from (\\d+) to (\\d+)".toRegex()

private fun d5a() {
    runVariant("a")
}

private fun d5b() {
   runVariant("b")
}

private fun runVariant(variant: String) {
    File("/Users/matejplch/Programming/aoc2022/src/main/resources/D5").useLines {
        val iterator = it.iterator()
        val initialConfig = mutableListOf<String>()
        initial@ while (iterator.hasNext()) {
            val line = iterator.next()
            if (line.startsWith(" 1")) {
                val lastIndex = line.lastIndexOf(" ")
                val numberOfStacks = line.substring(lastIndex + 1).toInt()
                stacks = Array(numberOfStacks) { ArrayDeque() }
                break@initial
            } else {
                initialConfig.add(line)
            }
        }
        initialConfig.forEach(::parseLine)
        iterator.next()
        while (iterator.hasNext()) {
            val line = iterator.next()
            val result = regex.matchEntire(line)!!
            val move = Move(result.groupValues[2].toInt(), result.groupValues[3].toInt(), result.groupValues[1].toInt())
            if (variant == "a") {
                makeMoveA(move)
            } else {
                makeMoveB(move)
            }
        }
        stacks.forEach { s -> print(s.last()) }
    }
}

private fun makeMoveA(move: Move) {
    repeat(move.count) {
        stacks[move.to - 1].addLast(stacks[move.from - 1].removeLast())
    }
}

private fun makeMoveB(move: Move) {
    val stack = stacks[move.from - 1]
    var index = move.count
    while (index > 0) {
        val item = stack.removeAt(stack.size - index)
        index --
        stacks[move.to - 1].addLast(item)
    }
}


private fun parseLine(line: String) {
    val arr = line.toCharArray()
    var linePosition = 0
    var itemNo = 0
    while (linePosition < arr.size) {
        linePosition++
        val value = arr[linePosition++]
        if (value != ' ') {
            stacks[itemNo].addFirst(value)
        }
        itemNo++
        linePosition += 2
    }
}

private data class Move(val from: Int, val to: Int, val count: Int)
