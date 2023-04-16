/**
 * https://www.acmicpc.net/problem/16919
 * 처음 풀고나서) 빡센 구현 문제, 다시 풀어볼 것ㅠㅠ
 * 다시 풀고나서) 구현, 규칙 찾기 => 초기 상태인 0,1분을 제외하고, 2분부터 4분 단위로 같은 상태가 반복된다
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
            if (graph[row][col] == 'O') {
                explosionMinutesTable[row][col] = 3
            }
        }
    }

    val lastMinutesModularFour = if (lastMinutes == 1) 1 else lastMinutes % 4 + 4
    simulateBomberMan(lastMinutesModularFour)
    printGraph()
}

private fun simulateBomberMan(lastMinutes: Int) {
    var currentMinutes = 2

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