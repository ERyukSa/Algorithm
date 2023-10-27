/**
 * 단기간 성장
 * https://www.acmicpc.net/workbook/view/4349
 */

/**
 * https://www.acmicpc.net/problem/2933 미네랄
 * 유형: BFS + 시뮬레이션
 * 클러스터 분리 여부를 확인하는 방법을 떠올리는 것이 핵심이자 어려운 부분이었다.
 * 바닥에 붙어있는 블록들을 찾음으로써 공중에 떠있는 클러스터를 찾는다는 역발상이 필요했다.
 * 또한, 2개 이상의 클러스터가 동시에 떨어지는 경우가 없다는 내용이 지문에 있었는데,
 * 문제를 이해하다가 이 부분을 잊어서 해결하는 데 시간이 더 걸렸다.
 * 문제를 다 이해하고 나서 지문을 다시 읽어보거나, 문제를 복잡하게 만드는 포인트를 발견했을 때 지문을 다시 확인해보자.
 */

import java.util.*


data class Position(val row: Int, val col: Int)

lateinit var cave: Array<CharArray>
lateinit var isLanded: Array<BooleanArray>
var landRow = 0
val queue = LinkedList<Position>()
val dRow = intArrayOf(-1, 1, 0, 0)
val dCol = intArrayOf(0, 0, -1, 1)

fun main() {
    val (rowSize, colSize) = readln().split(" ").map(String::toInt)
    cave = Array(rowSize) { readln().toCharArray() }
    isLanded = Array(rowSize) { BooleanArray(colSize) }
    landRow = rowSize - 1

    val arrowCount = readln().toInt()
    val st = StringTokenizer(readln())

    repeat(arrowCount) { i ->
        val arrowRow = rowSize - st.nextToken().toInt()
        breakMineral(i % 2, arrowRow)
        checkLandedBlock()
        makeClusterDown()
    }

    printCave()
}

// direction) 0: 왼쪽에서, 1: 오른쪽에서
fun breakMineral(direction: Int, row: Int) {
    if (direction == 0) {
        for (col in cave[0].indices) {
            if (cave[row][col] == 'x') {
                cave[row][col] = '.'
                return
            }
        }
    } else {
        for (col in cave[0].lastIndex downTo 0) {
            if (cave[row][col] == 'x') {
                cave[row][col] = '.'
                return
            }
        }
    }
}

fun makeClusterDown() {
    val clusterDownHeight = findClusterDownHeight()
    for (row in landRow downTo 0) {
        for (col in cave[0].indices) {
            if (cave[row][col] == 'x' && isLanded[row][col].not()) {
                cave[row][col] = '.'
                cave[row + clusterDownHeight][col] = 'x'
            }
        }
    }
}

fun checkLandedBlock() {
    resetIsLandedArray()

    for (col in isLanded[0].indices) {
        if (isLanded[landRow][col]) continue
        bfsToCheckLandedBlocks(col)
    }
}

fun bfsToCheckLandedBlocks(startCol: Int) {
    isLanded[landRow][startCol] = true
    queue.offer(Position(landRow, startCol))

    while (queue.isNotEmpty()) {
        val (row, col) = queue.poll()

        for (i in 0 until 4) {
            val nextRow = row + dRow[i]
            val nextCol = col + dCol[i]

            if (nextRow !in isLanded.indices || nextCol !in isLanded[0].indices) continue
            if (cave[nextRow][nextCol] == 'x' && isLanded[nextRow][nextCol].not()) {
                isLanded[nextRow][nextCol] = true
                queue.offer(Position(nextRow, nextCol))
            }
        }
    }
}

fun findClusterDownHeight(): Int {
    var clusterDownHeight = Int.MAX_VALUE

    for (row in cave.indices) {
        for (col in cave[0].indices) {
            if (cave[row][col] == 'x' && isLanded[row][col].not()) {
                clusterDownHeight = minOf(clusterDownHeight, findBlockDownHeight(row, col))
            }
        }
    }

    return clusterDownHeight
}

fun findBlockDownHeight(startRow: Int, col: Int): Int {
    var downHeight = 0

    for (row in startRow + 1 until isLanded.size) {
        if (cave[row][col] == '.') {
            downHeight++
        } else if (cave[row][col] == 'x' && isLanded[row][col]) {
            return downHeight
        } else {
            return Int.MAX_VALUE
        }
    }

    return downHeight
}

fun resetIsLandedArray() {
    for (row in isLanded.indices) {
        for (col in isLanded[0].indices) {
            isLanded[row][col] = false
        }
    }
}

fun printCave() {
    for (row in cave.indices) {
        for (col in cave[0].indices) {
            print(cave[row][col])
        }
        println()
    }
}