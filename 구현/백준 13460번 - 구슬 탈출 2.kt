/**
 * https://www.acmicpc.net/problem/13460
 * 빠악센 구현, 다시 풀 것
 * 문제) 보통 문제처럼 1칸씩 이동할 것이라고 섣불리 생각했다.
 * 개선) 당연하다고 생각하지 않기, 힌트를 모두 확인하자.
 */

import java.util.LinkedList

data class MarbleState(val redRow: Int, val redCol:Int, val blueRow: Int, val blueCol: Int, val moveCount: Int) {
    fun toVisitMask(): Int = (1000 * redRow) + (100 * redCol) + (10 * blueRow) + (1 * blueCol)
}

private lateinit var graph: Array<CharArray>
private val dRow = intArrayOf(-1, 1, 0, 0)
private val dCol = intArrayOf(0, 0, -1, 1)

fun main() {
    val (rowSize, _) = readln().split(" ").map(String::toInt)
    graph = Array(rowSize) { readln().toCharArray() }

    val initialMarbleState = getInitialMarbleState(graph)
    val answer = findMinMoveCount(initialMarbleState)
    print(answer)
}

private fun getInitialMarbleState(graph: Array<CharArray>): MarbleState {
    var (redRow, redCol) = (0 to 0)
    var (blueRow, blueCol) = (0 to 0)

    for (row in graph.indices) {
        for (col in graph[0].indices) {
            if (graph[row][col] == 'B') {
                blueRow = row
                blueCol = col
            } else if (graph[row][col] == 'R') {
                redRow = row
                redCol = col
            }
        }
    }

    return MarbleState(redRow, redCol, blueRow, blueCol, 0)
}

private fun findMinMoveCount(initialMarbleState: MarbleState): Int {
    val q = LinkedList<MarbleState>().apply {
        offer(initialMarbleState)
    }
    val visited = BooleanArray(8889).apply {
        this[initialMarbleState.toVisitMask()] = true
    }

    while (q.isNotEmpty()) {
        val marbleState = q.poll()

        for (dir in 0 until 4) {
            val nextMarbleState = getNextMarbleState(dir, marbleState)

            if (visited[nextMarbleState.toVisitMask()] || graph[nextMarbleState.blueRow][nextMarbleState.blueCol] == 'O') {
                continue
            }
            if (graph[nextMarbleState.redRow][nextMarbleState.redCol] == 'O') {
                return nextMarbleState.moveCount
            }
            if (marbleState.moveCount < 9) {
                visited[nextMarbleState.toVisitMask()] = true
                q.offer(nextMarbleState)
            }
        }
    }

    return -1
}

private fun getNextMarbleState(dir: Int, marbleState: MarbleState): MarbleState {
    var nextRedRow = marbleState.redRow + dRow[dir]
    var nextRedCol = marbleState.redCol + dCol[dir]
    var nextBlueRow = marbleState.blueRow + dRow[dir]
    var nextBlueCol = marbleState.blueCol + dCol[dir]

    when (dir) {
        0 -> {
            if (marbleState.redRow < marbleState.blueRow) {
                if (graph[nextRedRow][nextRedCol] == '#') nextRedRow += 1
                if (graph[nextBlueRow][nextBlueCol] == '#' || (nextRedRow == nextBlueRow && nextRedCol == nextBlueCol)) {
                    nextBlueRow += 1
                }
            } else {
                if (graph[nextBlueRow][nextBlueCol] == '#') nextBlueRow += 1
                if (graph[nextRedRow][nextRedCol] == '#' || (nextRedRow == nextBlueRow && nextRedCol == nextBlueCol)) {
                    nextRedRow += 1
                }
            }
        }
        1 -> {
            if (marbleState.redRow < marbleState.blueRow) {
                if (graph[nextBlueRow][nextBlueCol] == '#') nextBlueRow -= 1
                if (graph[nextRedRow][nextRedCol] == '#' || (nextRedRow == nextBlueRow && nextRedCol == nextBlueCol)) nextRedRow -= 1
            } else {
                if (graph[nextRedRow][nextRedCol] == '#') nextRedRow -= 1
                if (graph[nextBlueRow][nextBlueCol] == '#' || (nextRedRow == nextBlueRow && nextRedCol == nextBlueCol)) nextBlueRow -= 1
            }
        }
        2 -> {
            if (marbleState.redCol < marbleState.blueCol) {
                if (graph[nextRedRow][nextRedCol] == '#') nextRedCol += 1
                if (graph[nextBlueRow][nextBlueCol] == '#' || (nextRedRow == nextBlueRow && nextRedCol == nextBlueCol)) nextBlueCol += 1
            } else {
                if (graph[nextBlueRow][nextBlueCol] == '#') nextBlueCol += 1
                if (graph[nextRedRow][nextRedCol] == '#' || (nextRedRow == nextBlueRow && nextRedCol == nextBlueCol)) nextRedCol += 1
            }
        }
        else -> {
            if (marbleState.redCol < marbleState.blueCol) {
                if (graph[nextBlueRow][nextBlueCol] == '#') nextBlueCol -= 1
                if (graph[nextRedRow][nextRedCol] == '#' || (nextRedRow == nextBlueRow && nextRedCol == nextBlueCol)) nextRedCol -= 1
            } else {
                if (graph[nextRedRow][nextRedCol] == '#') nextRedCol -= 1
                if (graph[nextBlueRow][nextBlueCol] == '#' || (nextRedRow == nextBlueRow && nextRedCol == nextBlueCol)) nextBlueCol -= 1
            }
        }
    }

    return MarbleState(nextRedRow, nextRedCol, nextBlueRow, nextBlueCol,marbleState.moveCount + 1)
}