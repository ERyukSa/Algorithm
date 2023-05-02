/**
 * https://www.acmicpc.net/problem/1245
 * 그래프 탐색(DFS, BFS)
 * 높이가 같은 인접한 곳은 한 번의 재귀호출로 모두 탐색한다
 * 한번 호출 할 때마다 산봉우리인지 아닌지 여부를 반환한다.
 */
private lateinit var heights: Array<IntArray>
private lateinit var visited: Array<BooleanArray>
private val dRow = intArrayOf(-1, -1, 0, 1, 1, 1, 0, -1) // 시계 방향
private val dCol = intArrayOf(0, 1, 1, 1, 0, -1, -1, -1)

fun main() {
    val (rowSize, colSize) = readln().split(" ").map(String::toInt)
    heights = Array(rowSize) {
        readln().split(" ").map(String::toInt).toIntArray()
    }
    visited = Array(rowSize) { BooleanArray(colSize) }

    var topCount = 0
    for (row in 0 until rowSize) {
        for (col in 0 until colSize) {
            if (visited[row][col]) continue
            if (isTop(row, col)) {
                topCount += 1
            }
        }
    }

    print(topCount)
}

private fun isTop(row: Int, col: Int): Boolean {
    visited[row][col] = true
    var isHereTop = true

    for (i in 0 until 8) {
        val nextRow = row + dRow[i]
        val nextCol = col + dCol[i]
        if (nextRow !in heights.indices || nextCol !in heights[0].indices) continue

        // 산봉우리가 아님은 알아도 바로 리턴하지 않고
        // 높이가 같은 인접한 곳을 모두 방문하여 다음에 다시 방문하지 않도록 한다.
        if (heights[row][col] < heights[nextRow][nextCol]) {
            isHereTop = false
        } else if (heights[row][col] == heights[nextRow][nextCol] && visited[nextRow][nextCol].not()) {
            if (isTop(nextRow, nextCol).not()) {
                isHereTop = false
            }
        }
    }

    return isHereTop
}
