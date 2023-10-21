/**
 * 단기간 성장
 * https://www.acmicpc.net/workbook/view/4349
 */

/**
 * https://www.acmicpc.net/problem/3197 백조의 호수
 * 유형: DFS, BFS, 분리집합
 * 해결 실패 (시간 초과), 시간: 1시간
 * 녹이기와 백조 찾기에 대한 그래프 탐색을 각각 한 번씩만 진행해서 문제를 해결할 수 있다고 한다.
 * 나는 하루 지날 때마다 그래프 전체를 탐색하고 있다. 행렬 사이즈가 커지면 여러번 돌아야 해서 시간이 초과되는 듯 하다.
 * 조금만 더 고민해봐야겠다.
 */


import java.util.LinkedList

data class Location(val row: Int, val col: Int)

lateinit var lake: Array<CharArray>
lateinit var visited: Array<BooleanArray>
val dRow = intArrayOf(-1, 1, 0, 0)
val dCol = intArrayOf(0, 0, -1, 1)


fun main() {
    val (rowSize, colSize) = readln().split(" ").map(String::toInt)
    var swan1Row = 0
    var swan1Col = 0

    lake = Array(rowSize) { row ->
        readln().mapIndexed { col, ch ->
            if (ch == 'L') {
                swan1Row = row
                swan1Col = col
            }
            when (ch) {
                '.' -> 'W'
                'X' -> 'I'
                else -> 'L'
            }
        }.toCharArray()
    }
    visited = Array(rowSize) { BooleanArray(colSize) }
    var day = 0

    while (true) {
        if (canTheyMeet(swan1Row, swan1Col)) break
        meltIces()
        day++
    }

    print(day)
}

fun canTheyMeet(swan1Row: Int, swan1Col: Int): Boolean {
    initVisited()

    val queue = LinkedList<Location>()
    queue.offer(Location(swan1Row, swan1Col))
    visited[swan1Row][swan1Col] = true

    while (queue.isNotEmpty()) {
        val (row, col) = queue.poll()

        for (i in 0 until 4) {
            val nextRow = row + dRow[i]
            val nextCol = col + dCol[i]

            if (nextRow !in lake.indices || nextCol !in lake[0].indices || visited[nextRow][nextCol]) continue
            if (lake[nextRow][nextCol] == 'L') {
                return true
            }
            if (lake[nextRow][nextCol] == 'W') {
                visited[nextRow][nextCol] = true
                queue.offer(Location(nextRow, nextCol))
            }
        }
    }

    return false
}

fun meltIces() {
    initVisited()

    for (row in lake.indices) {
        for (col in lake[0].indices) {
            if (lake[row][col] == 'W' && !visited[row][col]) {
                visited[row][col] = true
                meltAround(row, col)
            }
        }
    }
}

fun meltAround(row: Int, col: Int) {
    for (i in 0 until 4) {
        val nextRow = row + dRow[i]
        val nextCol = col + dCol[i]

        if (nextRow in lake.indices && nextCol in lake[0].indices) {
            if (lake[nextRow][nextCol] == 'I') {
                lake[nextRow][nextCol] = 'W'
                visited[nextRow][nextCol] = true
            }
        }
    }
}

fun initVisited() {
    for (row in visited.indices) {
        for (col in visited[0].indices) {
            visited[row][col] = false
        }
    }
}