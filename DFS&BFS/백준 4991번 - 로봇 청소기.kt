/**
 * 단기간 성장
 * https://www.acmicpc.net/workbook/view/4349
 */

/**
 * https://www.acmicpc.net/problem/4991 로봇 청소기
 * 유형: bfs + 비트마스킹
 */

import java.util.LinkedList

data class State(val row: Int, val col: Int, val bitMask: Int, val moveCount: Int)

fun main() {
    val sb = StringBuilder()

    while (true) {
        val (width, height) = readln().split(" ").map(String::toInt)
        if (width == 0 || height == 0) {
            break
        }

        val room: Array<CharArray> = Array(height) { readln().toCharArray() }
        val (startRow, startCol) = getStartPosition(room)

        var dirtId = '0'
        for (row in 0 until height) {
            for (col in 0 until width) {
                if (room[row][col] == '*') {
                    room[row][col] = dirtId++
                }
            }
        }

        getMinMoveCount(room, startRow, startCol, dirtCount = dirtId - '0').let {
            sb.append(it).appendLine()
        }
    }

    print(sb.toString())
}

fun getStartPosition(room: Array<CharArray>): Pair<Int, Int> {
    for (row in room.indices) {
        for (col in room[0].indices) {
            if (room[row][col] == 'o') {
                return Pair(row, col)
            }
        }
    }
    return Pair(0, 0)
}

// bfs
fun getMinMoveCount(room: Array<CharArray>, startRow: Int, startCol: Int, dirtCount: Int): Int {
    val fullBitMask = 1.shl(dirtCount) - 1
    val queue = LinkedList<State>()
    // [row][column][dirt에 대한 bitmask]
    val visited: Array<Array<BooleanArray>> = Array(room.size) {
        Array(room[0].size) {
            BooleanArray(fullBitMask + 1)
        }
    }
    val dRow = intArrayOf(-1, 1, 0, 0)
    val dCol = intArrayOf(0, 0, -1, 1)

    queue.offer(State(startRow, startCol, 0, 0))
    visited[startRow][startCol][0] = true
    room[startRow][startCol] = '.'

    while (queue.isNotEmpty()) {
        val (row, col, bitMask, moveCount) = queue.poll()

        for (i in 0 until 4) {
            val nextRow = row + dRow[i]
            val nextCol = col + dCol[i]

            if (nextRow !in room.indices || nextCol !in room[0].indices || visited[nextRow][nextCol][bitMask]) continue
            if (room[nextRow][nextCol].isDigit()) {
                val newBitMask = bitMask or 1.shl(room[nextRow][nextCol] - '0')
                if (newBitMask == fullBitMask) {
                    return moveCount + 1
                } else {
                    queue.offer(State(nextRow, nextCol, newBitMask, moveCount + 1))
                    visited[nextRow][nextCol][newBitMask] = true
                }
            } else if (room[nextRow][nextCol] == '.') {
                queue.offer(State(nextRow, nextCol, bitMask, moveCount + 1))
                visited[nextRow][nextCol][bitMask] = true
            }
        }
    }

    return -1
}