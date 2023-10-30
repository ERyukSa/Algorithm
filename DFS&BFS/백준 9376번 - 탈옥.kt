/**
 * 단기간 성장
 * https://www.acmicpc.net/workbook/view/4349
 */

/**
 * https://www.acmicpc.net/problem/9376 탈옥
 * 해결X ㅠㅠ
 */

import java.util.*

data class State(val row: Int, val col: Int, val doorId: Int, val openCount: Int = 1, val prisonerMeetCount: Int = 0)

lateinit var prison: Array<CharArray>
lateinit var visited: Array<Array<Array<BooleanArray>>>
lateinit var queue: LinkedList<State>
val dRow = intArrayOf(-1, 1, 0, 0)
val dCol = intArrayOf(0, 0, -1, 1)

fun main() {
    val sb = StringBuilder()
    repeat(readln().toInt()) {
        val (rowSize, colSize) = readln().split(" ").map(String::toInt)
        setUp(rowSize, colSize)
        getMinDoorOpenCount().let { sb.append(it).appendLine() }
    }
    print(sb.toString())
}

fun setUp(rowSize: Int, colSize: Int) {
    visited = Array(rowSize) { Array(colSize) { Array(rowSize * colSize) { BooleanArray(2) } } }
    prison = Array(rowSize) { readln().toCharArray() }
    queue = LinkedList<State>()

    for (row in 0 until rowSize) {
        for (col in 0 until colSize) {
            if (row == 0 || row == rowSize - 1 || col == 0 || col == colSize - 1) {
                if (prison[row][col] == '#') {
                    queue.offer(State(row, col, row * col + col))
                    visited[row][col][row * col + col][0] = true
                    visited[row][col][row * col + col][1] = true
                }
            }
        }
    }
}

fun getMinDoorOpenCount(): Int {
    var minOpenCount = 0

    while (queue.isNotEmpty()) {
        val (row, col, doorId, openCount, prisonerMeetCount) = queue.poll()

        for (i in 0 until 4) {
            val nextRow = row + dRow[i]
            val nextCol = col + dCol[i]

            if (nextRow !in prison.indices || nextCol !in prison[0].indices || visited[nextRow][nextCol][doorId][prisonerMeetCount]) {
                continue
            }

            visited[nextRow][nextCol][doorId][prisonerMeetCount] = true

            if (prison[nextRow][nextCol] == '#') {
                if (visited[nextRow][nextCol][doorId][0] && prisonerMeetCount == 1) {
                    queue.addFirst(State(nextRow, nextCol, doorId, openCount, 1))
                } else {
                    queue.addLast(State(nextRow, nextCol, doorId, openCount + 1, prisonerMeetCount))
                }
            } else if (prison[nextRow][nextCol] == '.') {
                queue.addFirst(State(nextRow, nextCol, doorId, openCount, prisonerMeetCount))
            } else if (prison[nextRow][nextCol] == '$') {
                if (prisonerMeetCount == 1) {
                    return openCount
                }
                queue.addFirst(State(nextRow, nextCol, doorId, openCount, prisonerMeetCount + 1))
            }
        }
    }

    return minOpenCount
}