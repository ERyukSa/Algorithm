/**
 * https://www.acmicpc.net/problem/16724
 * 그래프 탐색, 유니온-파인드
 */
data class Node16724(val row: Int, val col: Int) {

    fun equals(otherRow: Int, otherCol: Int): Boolean =
        row == otherRow && col == otherCol
}

private lateinit var directionGraph: Array<IntArray>
private lateinit var parentOf: Array<Array<Node16724>>

private val dRow = intArrayOf(-1, 1, 0, 0)
private val dCol = intArrayOf(0, 0, -1, 1)

fun main() {
    val (height, width) = readln().split(" ").map(String::toInt)
    directionGraph = Array(height) {
        readln().map(::convertDirToInt).toIntArray()
    }
    parentOf = Array(height) { row ->
        Array(width) { col ->
            Node16724(row, col)
        }
    }

    for (row in 0 until height) {
        for (col in 0 until width) {
            if (parentOf[row][col].row == row && parentOf[row][col].col == col) {
                visit(row, col)
            }
        }
    }

    val parentSet = HashSet<Node16724>()
    for (row in 0 until height) {
        for (col in 0 until width) {
            parentSet.add(findParent(row, col))
        }
    }

    print(parentSet.size)
}

private fun visit(row: Int, col: Int) {
    val direction = directionGraph[row][col]
    val nextRow = row + dRow[direction]
    val nextCol = col + dCol[direction]
    val parentOfCurrent = findParent(row, col)
    val parentOfNext = findParent(nextRow, nextCol)
    if (parentOfCurrent != parentOfNext) {
        parentOf[nextRow][nextCol] = parentOfCurrent
        visit(nextRow, nextCol)
    }
}

private fun findParent(row: Int, col: Int): Node16724 {
    val parent = parentOf[row][col]
    if (parent.equals(row, col)) return parent
    parentOf[row][col] = findParent(parent.row, parent.col)
    return parentOf[row][col]
}

private fun convertDirToInt(ch: Char): Int {
    return when (ch) {
        'U' -> 0
        'D' -> 1
        'L' -> 2
        else -> 3
    }
}