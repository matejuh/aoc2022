import java.io.File
import kotlin.math.floor
import kotlin.math.roundToInt

fun main() {
    d11a()
}

private val monkeys = mutableListOf<Monkey>()

private fun d11a() {
    File(ClassLoader.getSystemResource("D11").path).useLines {
        val iterator = it.iterator()
        while (iterator.hasNext()) {
            monkeys.add(readMonkey(iterator))
        }
    }
    repeat(20) { round ->
        println("ROUND: $round")
        monkeys.forEachIndexed { index, monkey ->
//            println("Monkey no. $index")
            monkey.inspectAll()
        }
    }
    println(monkeys)
}

//Monkey 1:
//  Starting items: 54, 65, 75, 74
//  Operation: new = old + 6
//  Test: divisible by 19
//    If true: throw to monkey 2
//    If false: throw to monkey 0
private fun readMonkey(iterator: Iterator<String>): Monkey {
    iterator.next()
    val items = iterator.next().split(": ")[1].split(", ").map { it.toInt() }
    val operationInput = iterator.next().split(": ")[1].split(" = ")[1].split(" ")
    val mathOperation = operationInput[1]
    val param = operationInput[2]
    val operation: (Int) -> Int = if (param == "old") {
        when (mathOperation) {
            "+" -> { old: Int -> old + old }
            "*" -> { old: Int -> old * old }
            else -> throw IllegalStateException("Unsupported operation: $mathOperation")
        }
    } else {
        val number = param.toInt()
        when (mathOperation) {
            "+" -> { old: Int -> old + number }
            "*" -> { old: Int -> old * number }
            else -> throw IllegalStateException("Unsupported operation: $mathOperation")
        }
    }
    val divisibleBy = iterator.next().substringAfterLast(" ").toInt()
    val trueMonkey = iterator.next().substringAfterLast(" ").toInt()
    val falseMonkey = iterator.next().substringAfterLast(" ").toInt()
    val test = { level: Int ->
        if (level % divisibleBy == 0) {
            trueMonkey
        } else {
            falseMonkey
        }
    }
    if (iterator.hasNext()) {
        iterator.next()
    }
    return Monkey(ArrayDeque(items), operation, test)
}

private class Monkey(val items: ArrayDeque<Int>, val operation: (Int) -> Int, val test: (Int) -> Int) {
    var inspectedItems = 0
    override fun toString(): String = "Monkey inspected items: $inspectedItems"

    fun inspectAll() {
        do {
            val item = items.removeFirstOrNull()
            if (item != null) {
                val worryLevel = operation(item)
//            println("Worry level: $worryLevel")
                val currentWorryLevel = floor(worryLevel / 3.0).roundToInt()
//            println("Current level: $currentWorryLevel")
                val nextMonkey = test(currentWorryLevel)
//            println("Next monkey: $nextMonkey")
                monkeys[nextMonkey].addItem(currentWorryLevel)
                inspectedItems++
            }
        } while (
            item != null
        )
    }

    fun addItem(item: Int) {
        items.addLast(item)
    }
}
