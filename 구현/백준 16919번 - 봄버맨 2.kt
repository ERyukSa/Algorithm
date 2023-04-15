/**
 * https://www.acmicpc.net/problem/16919
 * 빡센 구현 문제, 다시 풀어볼 것ㅠㅠ
 */
import java.lang.StringBuilder

private lateinit var graph: Array<CharArray>
private lateinit var explosionMinutesTable: Array<IntArray>
private val dRow = intArrayOf(-1, 1, 0, 0)
private val dCol = intArrayOf(0, 0, -1, 1)

fun main() {
    val (rowSize, colSize, lastMinutes) = readln().split(" ").map(String::toInt)
    graph = Array(rowSize) {
        readln().toCharArray()
    }
    explosionMinutesTable = Array(rowSize) { IntArray(colSize) { -1 } }

    for (row in graph.indices) {
        for (col in graph[0].indices) {
            if (graph[row][col] == '.') {
                explosionMinutesTable[row][col] = 5
            } else {
                explosionMinutesTable[row][col] = 3
            }
        }
    }

    simulateBomberMan(lastMinutes)
    printGraph()
}

private fun simulateBomberMan(lastMinutes: Int) {
    var currentMinutes = 3

    while (currentMinutes <= lastMinutes) {
        if (currentMinutes % 2 == 0) {
            setUpBombs(currentMinutes)
        } else {
            for (row in graph.indices) {
                for (col in graph[0].indices) {
                    if (explosionMinutesTable[row][col] == currentMinutes) {
                        explode(row, col, currentMinutes)
                    }
                }
            }
        }

        currentMinutes += 1
    }
}

private fun setUpBombs(currentMinutes: Int) {
    for (row in explosionMinutesTable.indices) {
        for (col in explosionMinutesTable[0].indices) {
            if (explosionMinutesTable[row][col] == -1) {
                explosionMinutesTable[row][col] = currentMinutes + 3
            }
        }
    }
}

private fun explode(row: Int, col: Int, currentMinutes: Int) {
    if (explosionMinutesTable[row][col] != currentMinutes) {
        return
    }
    explosionMinutesTable[row][col] = -1

    for (i in 0 until 4) {
        val nextRow = row + dRow[i]
        val nextCol = col + dCol[i]

        if (nextRow !in graph.indices || nextCol !in graph[0].indices) continue
        if (explosionMinutesTable[nextRow][nextCol] != currentMinutes) {
            explosionMinutesTable[nextRow][nextCol] = -1
        }
    }
}

private fun printGraph() {
    val sb = StringBuilder()
    for (row in explosionMinutesTable.indices) {
        for (col in explosionMinutesTable[0].indices) {
            if (explosionMinutesTable[row][col] == -1) {
                sb.append('.')
            } else {
                sb.append('O')
            }
        }
        sb.appendLine()
    }
    print(sb.toString())
}