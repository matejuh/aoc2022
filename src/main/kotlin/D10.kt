import java.io.File

fun main() {
    d10a()
}

private fun d10a() {
    val cpu = Processor()
    File(ClassLoader.getSystemResource("D10").path).forEachLine { line ->
        val cmd: Cmd = if (line == "noop") {
            Noop
        } else {
            val (command, number) = line.split(" ")
            Add(number.toInt())
        }
        cpu.process(cmd)
    }
    println(cpu.cyclesSum)
}

private interface Cmd

private object Noop: Cmd

data class Add(val value: Int): Cmd

private class Processor {
    private var cycles = setOf(20, 60, 100, 140, 180, 220)
    var cyclesSum = 0

    private var cycle = 0
    private var sum = 1

    fun process(cmd: Cmd) {
        when(cmd) {
            is Noop -> incrementCycle()
            is Add -> {
                incrementCycle()
                incrementCycle()
                sum += cmd.value
            }
        }
    }

    private fun incrementCycle() {
        cycle ++
        println("cycle: $cycle $sum")
        if (cycles.contains(cycle)) {
            cyclesSum += (cycle * sum)
        }
    }
}
