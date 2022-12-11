import java.io.File
import kotlin.math.floor
import kotlin.math.roundToLong

fun main() {
//    d11a()
    d11b()
}

private val monkeys = mutableListOf<Monkey>()

private fun d11a() {
    File(ClassLoader.getSystemResource("D11test").path).useLines {
        val iterator = it.iterator()
        while (iterator.hasNext()) {
            monkeys.add(readMonkey(iterator))
        }
    }
    val commonMultiplier = monkeys.fold(1) { cur, monkey -> cur * monkey.divisibleBy } // all prime numbers
    repeat(20) { round ->
//        println("ROUND: $round")
        monkeys.forEachIndexed { index, monkey ->
//            println("Monkey no. $index")
            monkey.inspectAll(3.0, commonMultiplier)
        }
    }
    monkeys.forEachIndexed { index, monkey ->
        println("Monkey no. $index inspected items ${monkey.inspectedItems} times")
    }
}

private fun d11b() {
    File(ClassLoader.getSystemResource("D11").path).useLines {
        val iterator = it.iterator()
        while (iterator.hasNext()) {
            monkeys.add(readMonkey(iterator))
        }
    }
    val commonMultiplier = monkeys.fold(1) { cur, monkey -> cur * monkey.divisibleBy } // all prime numbers
    println(commonMultiplier)
    repeat(10000) { round ->
        println("ROUND: $round")
        monkeys.forEachIndexed { index, monkey ->
            println("Monkey no. $index")
            monkey.inspectAll(1.0, commonMultiplier)
        }
    }
    monkeys.forEachIndexed { index, monkey ->
        println("Monkey no. $index inspected items ${monkey.inspectedItems} times")
    }
}

//Monkey 1:
//  Starting items: 54, 65, 75, 74
//  Operation: new = old + 6
//  Test: divisible by 19
//    If true: throw to monkey 2
//    If false: throw to monkey 0
private fun readMonkey(iterator: Iterator<String>): Monkey {
    iterator.next()
    val items = iterator.next().split(": ")[1].split(", ").map { it.toLong() }
    val operationInput = iterator.next().split(": ")[1].split(" = ")[1].split(" ")
    val mathOperation = operationInput[1]
    val param = operationInput[2]
    val operation: (Long) -> Long = if (param == "old") {
        when (mathOperation) {
            "+" -> { old: Long -> old + old }
            "*" -> { old: Long -> old * old }
            else -> throw IllegalStateException("Unsupported operation: $mathOperation")
        }
    } else {
        val number = param.toInt()
        when (mathOperation) {
            "+" -> { old: Long -> old + number }
            "*" -> { old: Long -> old * number }
            else -> throw IllegalStateException("Unsupported operation: $mathOperation")
        }
    }
    val divisibleBy = iterator.next().substringAfterLast(" ").toInt()
    val trueMonkey = iterator.next().substringAfterLast(" ").toInt()
    val falseMonkey = iterator.next().substringAfterLast(" ").toInt()
    val test = { level: Long ->
        if (level % divisibleBy == 0L) {
            trueMonkey
        } else {
            falseMonkey
        }
    }
    if (iterator.hasNext()) {
        iterator.next()
    }
    return Monkey(ArrayDeque(items), divisibleBy, operation, test)
}

private class Monkey(
    val items: ArrayDeque<Long>,
    val divisibleBy: Int,
    val operation: (Long) -> Long,
    val test: (Long) -> Int
) {
    var inspectedItems = 0
    override fun toString(): String = "Monkey inspected items: $inspectedItems"

    fun inspectAll(divider: Double, commonMultiplier: Int) {
        do {
            val item = items.removeFirstOrNull()
            if (item != null) {
//                println(item)
                val worryLevel = operation(item)
//                println("Worry level: $worryLevel")
                val currentWorryLevel = floor(worryLevel / divider).roundToLong() % commonMultiplier
//                println("Current level: $currentWorryLevel")
                val nextMonkey = test(currentWorryLevel)
//                println("Next monkey: $nextMonkey")
//                println("$currentWorryLevel $nextMonkey")
                monkeys[nextMonkey].addItem(currentWorryLevel)
                inspectedItems++
            }
        } while (
            item != null
        )
    }

    fun addItem(item: Long) {
        items.addLast(item)
    }
}
