import java.io.File

fun main() {
//    d8a()
    d8b()
}

lateinit var grid: Array<IntArray>
var size: Int = 0

private fun initializeGrid() {
    var row = 0
    File(ClassLoader.getSystemResource("D8").path).forEachLine { line ->
        if (!::grid.isInitialized) {
            size = line.length
            grid = Array(line.length) { IntArray(line.length) }
        }
        line.forEachIndexed { column, c -> grid[row][column] = c.digitToInt() }
        row++
    }
}

private fun isEdge(rowIndex: Int, columnIndex: Int): Boolean =
    rowIndex == 0 || rowIndex == size - 1 || rowIndex == size - 1 || columnIndex == 0 || columnIndex == size - 1

private fun isVisible(range: IntRange, condition: (i: Int) -> Boolean): Boolean {
    for (i in range) {
        if (condition(i)) {
            return false
        }
    }
    return true
}

private fun isVisible(rowIndex: Int, columnIndex: Int, current: Int): Boolean =
    isEdge(rowIndex, columnIndex)
            || isVisible(0 until rowIndex) { i -> grid[i][columnIndex] >= current }
            || isVisible(0 until columnIndex) { i -> grid[rowIndex][i] >= current}
            || isVisible(columnIndex + 1 until size) { i -> grid[rowIndex][i] >= current }
            || isVisible(rowIndex + 1 until size) { i -> grid[i][columnIndex] >= current}

private fun d8a() {
    initializeGrid()
    var visible = 0
    grid.forEachIndexed { rowIndex, rowLine ->
        rowLine.forEachIndexed { columnIndex, current ->
            if (isVisible(rowIndex, columnIndex, current)) {
                visible++
            }
        }
    }
    println(visible)
}

private fun directionScore(range: IntProgression, condition: (i: Int) -> Boolean): Int {
    var score = 0
    loop@for (i in range) {
        score ++
        if (condition(i)) {
            break@loop
        }
    }
    return score
}

private fun treeScore(rowIndex: Int, columnIndex: Int, current: Int): Int =
    if (isEdge(rowIndex, columnIndex)) {
        0
    } else {
        directionScore(columnIndex - 1 downTo 0) { i -> grid[rowIndex][i] >= current } *
        directionScore(rowIndex - 1 downTo 0) { i -> grid[i][columnIndex] >= current } *
        directionScore(columnIndex + 1 until size) { i -> grid[rowIndex][i] >= current } *
        directionScore(rowIndex + 1 until size) { i -> grid[i][columnIndex] >= current}
    }


private fun d8b() {
    initializeGrid()
//    println(viewScore(1, 2, 5))

    var max = 0
    grid.forEachIndexed { rowIndex, rowLine ->
        rowLine.forEachIndexed { columnIndex, current ->
            val treeScore = treeScore(rowIndex, columnIndex, current)
            if (treeScore > max) {
                max = treeScore
            }
        }
    }
    println(max)
}
