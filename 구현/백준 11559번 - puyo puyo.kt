/**
 * https://www.acmicpc.net/problem/11559
 * 그래프 탐색 기반 빡구현
 */
private lateinit var puyoGraph: Array<CharArray>
private val visited = Array(12) { BooleanArray(6) }
private val dRow = intArrayOf(-1, 1, 0, 0)
private val dCol = intArrayOf(0, 0, -1, 1)

fun main() {
    puyoGraph = Array(12) {
        readln().toCharArray()
    }

    var count = 0
    while (true) {
        var hasSequentRemove = false

        for (row in puyoGraph.indices) {
            for (column in puyoGraph[0].indices) {
                if (puyoGraph[row][column] == '.' || visited[row][column]) {
                    continue
                }
                val removeCount = countSequentPuyos(row, column, puyoGraph[row][column])
                if (removeCount >= 4) {
                    hasSequentRemove = true
                    removePuyos(row, column, puyoGraph[row][column])
                }
            }
        }

        if (hasSequentRemove) {
            count++
            pullDownPuyos()
            visited.initiate()
        } else {
            break
        }
    }

    print(count)
}

private fun countSequentPuyos(row: Int, col: Int, puyo: Char): Int {
    visited[row][col] = true
    var removeCount = 1

    for (i in 0 until 4) {
        val nextRow = row + dRow[i]
        val nextCol = col + dCol[i]

        if (nextRow !in puyoGraph.indices || nextCol !in puyoGraph[0].indices) continue
        if (visited[nextRow][nextCol] || puyoGraph[nextRow][nextCol] != puyo) continue

        removeCount += countSequentPuyos(nextRow, nextCol, puyo)
    }

    return removeCount
}

private fun removePuyos(row: Int, col: Int, puyo: Char) {
    puyoGraph[row][col] = '.'

    for (i in 0 until 4) {
        val nextRow = row + dRow[i]
        val nextCol = col + dCol[i]

        if (nextRow !in puyoGraph.indices || nextCol !in puyoGraph[0].indices) continue
        if (puyoGraph[nextRow][nextCol] != puyo) continue

        removePuyos(nextRow, nextCol, puyo)
    }
}

private fun pullDownPuyos() {
    for (column in 0 until 6) {
        for (targetRow in 11 downTo 1) {
            if (puyoGraph[targetRow][column].isPuyo()) {
                continue
            }

            for (row in targetRow - 1 downTo 0) {
                if (puyoGraph[row][column].isPuyo()) {
                    puyoGraph[targetRow][column] = puyoGraph[row][column]
                    puyoGraph[row][column] = '.'
                    break
                }
            }
        }
    }
}

private fun Char.isPuyo(): Boolean = this != '.'
private fun Array<BooleanArray>.initiate() {
    for (row in this.indices) {
        for (col in this[0].indices) {
            this[row][col] = false
        }
    }
}
