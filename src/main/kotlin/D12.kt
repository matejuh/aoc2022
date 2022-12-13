import java.io.File

fun main() {
    d12a()
}

private var shortestPath: Int = -1

fun d12a() {
    val columns = File(ClassLoader.getSystemResource("D12").path).readLines()
        .mapIndexed { idy, row ->
            row.toCharArray().mapIndexed { idx, ch -> Node(ch, Pair(idy, idx)) }
        }
    lateinit var start: Node
    columns.forEachIndexed { idy, row ->
        row.forEachIndexed { idx, node ->
            if (node.sign == 'S') {
                start = node
            }
            val children = listOf(idy + 1 to idx, idy to idx - 1, idy - 1 to idx, idy to idx + 1)
                .mapNotNull { (y, x) ->
                    if (y >= 0 && y < columns.size && x >= 0 && x < row.size) {
                        val child = columns[y][x]
                        if (child.value - node.value <= 1) {
                            child
                        } else {
                            null
                        }
                    } else {
                        null
                    }
                }
            if (children.isNotEmpty()) {
                node.children.addAll(children)
            }
        }
    }

//    columns.forEach { row -> row.forEach { node -> println("${node.sign} ${node.value} ${node.children}")} }

    iterate(start)
//    columns.forEach { row ->
//        row.forEach { node -> print(if (node.path > 0) "1" else node.sign ) }
//        println()
//    }

    println(shortestPath)
}

private fun iterate(node: Node) {
    val queue = ArrayDeque<Node>()
    queue.add(node)
    val visited = mutableSetOf<Node>()


    while (queue.isNotEmpty()) {
        val currentNode = queue.removeFirst()

        if (visited.contains(currentNode)) continue
        if (currentNode.sign == 'E') {
            shortestPath = currentNode.path
            return
        } else {
            currentNode.children.forEach {
                it.path = currentNode.path + 1
                queue.addLast(it)
            }
        }
        visited.add(currentNode)
    }
}

private data class Node(val sign: Char, val coords: Pair<Int, Int>) {
    val value: Int = when (sign) {
        'S' -> 'a'.code
        'E' -> 'z'.code
        else -> sign.code
    } - 'a'.code + 1
    val children = mutableListOf<Node>()
    var path = 0
}
