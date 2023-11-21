/**
 * 단기간 성장
 * https://www.acmicpc.net/workbook/view/4349
 */

/**
 * https://www.acmicpc.net/problem/2931 가스관
 * 유형: 시뮬레이션, 구현
 */

import java.util.LinkedList

data class Location(val row: Int, val col: Int, val direction: Int)

lateinit var graph: Array<CharArray>
lateinit var visited: Array<BooleanArray>
val dRow = intArrayOf(-1, 1, 0, 0) // 위, 아래, 왼, 오른
val dCol = intArrayOf(0, 0, -1, 1)

fun main() {
    val (rowSize, colSize) = readln().split(" ").map(String::toInt)
    graph = Array(rowSize) { readln().toCharArray() }
    visited = Array(rowSize) { BooleanArray(colSize) }

    var mRow = 0
    var mCol = 0
    var zRow = 0
    var zCol = 0
    for (row in 0 until rowSize) {
        for (col in 0 until colSize) {
            if (graph[row][col] == 'M') {
                mRow = row
                mCol = col
            } else if (graph[row][col] == 'Z') {
                zRow = row
                zCol = col
            }
        }
    }

    val stolenLocationByM = findStolenBlockLocation(mRow, mCol)
    val stolenLocationByZ = findStolenBlockLocation(zRow, zCol)
    val stolenBlockType = calculateStolenBlockType(
        stolenLocationByM.row,
        stolenLocationByM.col,
        stolenLocationByM.direction,
        stolenLocationByZ.direction
    )

    print("${stolenLocationByM.row + 1} ${stolenLocationByM.col + 1} $stolenBlockType")
}

// 시뮬레이션으로 훔친 위치 반환
fun findStolenBlockLocation(startRow: Int, startCol: Int): Location {
    val q = LinkedList<Location>()
    visited[startRow][startCol] = true

    // 시작 위치(M or Z)에 연결된 인접한 가스관 찾기
    for (direction in 0 until 4) {
        val nextRow = startRow + dRow[direction]
        val nextCol = startCol + dCol[direction]

        if (nextRow in graph.indices && nextCol in graph[0].indices && graph[nextRow][nextCol] in "|-+1234") {
            q.offer(Location(nextRow, nextCol, direction))
            visited[nextRow][nextCol] = true
        }
    }

    while (q.isNotEmpty()) {
        val (row, col, direction) = q.poll()
        val nextDirection = getNextDirection(graph[row][col], direction)
        val nextLocation = Location(row + dRow[nextDirection], col + dCol[nextDirection], nextDirection)

        if (graph[nextLocation.row][nextLocation.col] == '.') {
            return nextLocation
        }

        if (graph[nextLocation.row][nextLocation.col] in "MZ") continue

        q.offer(nextLocation)
        visited[nextLocation.row][nextLocation.col] = true
    }

    return Location(-1, -1, -1)
}

// 현재 위치의 블록 타입과 방향으로 다음 방향 계산
// 방향 - 0: 위, 1: 아래, 2: 왼쪽, 3: 오른쪽
fun getNextDirection(blockType: Char, currentDirection: Int): Int {
    return when (blockType) {
        '|', '-', '+' -> currentDirection
        '1' -> if (currentDirection == 2) 1 else 3
        '2' -> if (currentDirection == 1) 3 else 0
        '3' -> if (currentDirection == 3) 0 else 2
        '4' -> if (currentDirection == 0) 2 else 1
        else -> throw IllegalArgumentException()
    }
}

// 훔친 블록 타입 최종 계산
fun calculateStolenBlockType(stolenRow: Int, stolenCol: Int, direction1: Int, direction2: Int): Char {
    // 훔친 공간에 주변에 방문하지 않은 가스관이 있으면 '+' 반환
    for (direction in 0 until 4) {
        val nextRow = stolenRow + dRow[direction]
        val nextCol = stolenCol + dCol[direction]

        if (nextRow !in graph.indices || nextCol !in graph[0].indices) continue
        if (graph[nextRow][nextCol] != '.' && visited[nextRow][nextCol].not()) {
            return '+'
        }
    }

    return when(
        if (direction1 > direction2) "$direction2$direction1" else "$direction1$direction2"
    ) {
        "01" -> '|'
        "23" -> '-'
        "02" -> '1'
        "12" -> '2'
        "13" -> '3'
        "03" -> '4'
        else -> '0'
    }
}