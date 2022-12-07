import java.util.function.Predicate

fun main() {
    d7b()
}

private val fileSystem = FileSystem()

private fun d7a() {
    initializeFileSystem()
//    fileSystem.printNode(fileSystem.root)
    println(fileSystem.sum(fileSystem.root) { item: Directory -> item.size < 100000 })
}

private fun d7b() {
    initializeFileSystem()
    val currentSize = fileSystem.root.size
    val needToDelete = 70000000 - 30000000 - currentSize
    if (needToDelete > 0) {
        // No need to delete anything
    } else {
        println(fileSystem.findClosest(fileSystem.root, needToDelete * -1, fileSystem.root.size))
    }

}

private fun initializeFileSystem() {
    java.io.File("/Users/matejplch/Programming/aoc2022/src/main/resources/D7").useLines {
        val iterator = it.iterator()
        var command = processCommand(iterator.next())
        while (iterator.hasNext()) {
            command.run()
            do {
                val output = iterator.next()
                if (output.startsWith("$ ")) {
                    command = processCommand(output)
                    break
                } else {
                    command.processOutput(output)
                }

            } while (iterator.hasNext())
        }
    }
}

private fun processCommand(line: String): Command {
    val commandWithArguments = line.substring(2).split(" ")
    return when (val cmd = commandWithArguments.first()) {
        "cd" -> ChangeDirectory(commandWithArguments[1])
        "ls" -> ListDirectory()
        else -> throw IllegalArgumentException("Unknown command $cmd")
    }
}

private interface Command {
    fun run()
    fun processOutput(line: String)
}

private class ListDirectory() : Command {
    override fun run() {}

    override fun processOutput(line: String) {
        val o = line.split(" ")
//        dir a
//        14848514 b.txt
        val currentNode = fileSystem.currentNode
        val child = if (o[0] == "dir") {
            Directory(o[1], currentNode)
        } else {
            File(o[1], currentNode, o[0].toInt())
        }
        currentNode.addChild(child)
    }
}

private class ChangeDirectory(private val dir: String) : Command {
    override fun run() {
        fileSystem.currentNode = when (dir) {
            ".." -> fileSystem.currentNode.parent!!
            "/" -> {
                Directory("/", null).also {
                    fileSystem.root = it
                }
            }
            else -> fileSystem.currentNode.getChild(dir)
        }
    }

    override fun processOutput(line: String) {}
}

private class FileSystem {
    lateinit var currentNode: Directory
    lateinit var root: Directory

    fun printNode(node: FileSystemNode) {
        when (node) {
            is Directory -> {
                println("Dir: ${node.name}")
                node.getChilden().forEach(::printNode)
            }
            is File -> println("File: $node")
        }
    }

    fun findClosest(node: Directory, limit: Int, minimum: Int): Int {
        val currentNodeSize = node.size
        return if (currentNodeSize - limit > 0) {
            if (currentNodeSize <= minimum) {
                node.getChilden()
                    .filterIsInstance<Directory>()
                    .map { findClosest(it, limit, currentNodeSize) }
                    .fold(currentNodeSize) { acc, value -> if (acc < value) acc else value }
            } else {
                minimum
            }
        } else {
            minimum
        }
    }

    fun sum(node: Directory, predicate: Predicate<Directory>): Int {
        var sum = 0
        sum += if (predicate.test(node)) {
            node.size
        } else {
            0
        }
        sum += node.getChilden().filterIsInstance<Directory>().map { sum(it, predicate) }
            .fold(0) { acc, it -> acc + it }

        return sum
    }
}

private interface FileSystemNode {
    val size: Int
    val name: String
    val parent: Directory?
}

private data class File(override val name: String, override val parent: Directory?, override val size: Int) :
    FileSystemNode

private data class Directory(override val name: String, override val parent: Directory?) : FileSystemNode {
    private val children: MutableMap<String, FileSystemNode> = mutableMapOf()
    override val size: Int
        get() = children.values.fold(0) { size, node -> size + node.size }

    fun addChild(child: FileSystemNode) {
        children[child.name] = child
    }

    fun getChild(name: String): Directory = children[name]!! as Directory

    fun getChilden(): MutableCollection<FileSystemNode> = children.values
}
