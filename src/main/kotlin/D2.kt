import java.io.File

fun main() {
    d2b()
}

fun d2A() {
    val file = File(ClassLoader.getSystemResource("D2").path)
    var finalScore = 0
    file.forEachLine {
        if(it.isNotBlank()) {
            finalScore += when (it) {
                "A X" -> 1 + 3
                "A Y" -> 2 + 6
                "A Z" -> 3 + 0
                "B X" -> 1 + 0
                "B Y" -> 2 + 3
                "B Z" -> 3 + 6
                "C X" -> 1 + 6
                "C Y" -> 2 + 0
                "C Z" -> 3 + 3
                else -> throw IllegalStateException("Should not happen $it!")
            }
        }
    }
    println(finalScore)
}

fun d2b() {
    val file = File(ClassLoader.getSystemResource("D2").path)
    var finalScore = 0
    file.forEachLine {
        if(it.isNotBlank()) {
            finalScore += when (it) {
                "A X" -> 0 + 3
                "A Y" -> 3 + 1
                "A Z" -> 6 + 2
                "B X" -> 0 + 1
                "B Y" -> 3 + 2
                "B Z" -> 6 + 3
                "C X" -> 0 + 2
                "C Y" -> 3 + 3
                "C Z" -> 6 + 1
                else -> throw IllegalStateException("Should not happen $it!")
            }
        }
    }
    println(finalScore)
}

//X means you need to lose, Y means you need to end the round in a draw, and Z means you need to win. Good luck!"

//(1 for Rock, 2 for Paper, and 3 for Scissors) plus the score for the outcome of the round (0 if you lost, 3 if the round was a draw, and 6 if you won)
//win. "The first column is what your opponent is going to play: A for Rock, B for Paper, and C for Scissors. The second column--" Suddenly, the Elf is called away to help with someone's tent.
//
//The second column, you reason, must be what you should play in response: X for Rock, Y for Paper, and Z for Scissors. Winning every time would be suspicious, so the responses must have been carefully chosen.
//
