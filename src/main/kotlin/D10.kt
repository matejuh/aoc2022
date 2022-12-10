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
}

private interface Cmd

private object Noop: Cmd

data class Add(val value: Int): Cmd

private class Processor {
    private var cycle = 0

    private var spriteBeginning = 1

    fun process(cmd: Cmd) {
        when(cmd) {
            is Noop -> {
                incrementCycle()
            }
            is Add -> {
                incrementCycle()
                incrementCycle()
                val tmp = spriteBeginning + cmd.value
                spriteBeginning = tmp
            }
        }
    }

    private fun incrementCycle() {
        if ((cycle % 40) in (spriteBeginning -1) .. (spriteBeginning + 1)) {
            print("#")
        } else {
            print(" ")
        }
        if (cycle % 40 == 39) {
            println()
        }
        cycle ++
    }
}

//##..##..##..##..##..##..##..##..##..##..
//###...###...###...###...###...###...###.
//####....####....####....####....####....
//#####.....#####.....#####.....#####.....
//######......######......######......####
//#######.......#######.......#######.....

//###  ####  ##  ###  #  # ###  #### ###
//#  #    # #  # #  # # #  #  # #    #  #
//#  #   #  #    #  # ##   #  # ###  ###
//###   #   # ## ###  # #  ###  #    #  #
//#    #    #  # #    # #  #    #    #  #
//#    ####  ### #    #  # #    #### ###
