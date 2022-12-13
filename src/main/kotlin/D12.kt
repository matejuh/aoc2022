import java.io.File

fun main() {
    d12a()
}

private var shortestPath: Int = -1

fun d12a() {
    val columns = File(ClassLoader.getSystemResource("D12test").path).readLines()
        .mapIndexed { idy, row ->
            row.toCharArray().mapIndexed { idx, ch -> Node(ch, Pair(idy, idx)) }
        }
    lateinit var start: Node
    columns.forEachIndexed { idy, row ->
        row.forEachIndexed { idx, node ->
            if (node.sign == 'S') {
                start = node
            }
            val children = listOf(idy + 1 to idx, idy - 1 to idx, idy to idx - 1, idy to idx + 1)
                .mapNotNull { (y, x) ->
                    if (y >= 0 && y < columns.size && x >= 0 && x < row.size) {
                        val child = columns[y][x]
                        if (child.value - node.value in 0..1) {
                            child
                        } else {
                            null
                        }
                    } else {
                        null
                    }
                }
            if (children.isNotEmpty()) {
                node.children.addAll(children.sortedByDescending { it.value })
            }
        }
    }

//    columns.forEach { row -> row.forEach { node -> println("${node.sign} ${node.value} ${node.children}")} }

    iterate(start, emptyList())
    println(shortestPath)
}

private fun iterate(node: Node, visitedNodes: List<Node>) {
//    println("$node ${visitedNodes.size}")
    if (node.sign == 'E') {
        println("Found " + visitedNodes.size)
        if (shortestPath == -1 || visitedNodes.size < shortestPath) {
            shortestPath = visitedNodes.size
        }
    } else {
        if (shortestPath != -1 && visitedNodes.size > shortestPath) {
            return
        } else {
            val nodes = visitedNodes + node
            node.children.filter { !visitedNodes.contains(it) }.forEach {
                iterate(it, nodes)
            }
        }
    }
}

private data class Node(val sign: Char, val coords: Pair<Int, Int>) {
    val value: Int = when (sign) {
        'S' -> 0
        'E' -> 27
        else -> sign.code - 96
    }
    val children = mutableListOf<Node>()
}
