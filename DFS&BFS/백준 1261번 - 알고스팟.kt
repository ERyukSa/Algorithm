/**
 * https://www.acmicpc.net/problem/1261
 * 다익스트라 or 0-1 BFS(좀 더 빠름)
 */
import java.util.LinkedList

fun main() {
    val (_, rowSize) = readln().split(" ").map(String::toInt)
    val maze = Array(rowSize) {
        readln().toCharArray()
    }
    getMinBrokenWallCount(maze).let { print(it) }
}

private fun getMinBrokenWallCount(maze: Array<CharArray>): Int {

    data class State(val row: Int, val col: Int, val brokenWallCount: Int)
    val dRow = intArrayOf(-1, 1, 0, 0)
    val dCol = intArrayOf(0, 0, -1, 1)

    val q = LinkedList<State>()
    val visited = Array(maze.size) { BooleanArray(maze[0].size) }
    q.offer(State(0, 0, 0))
    visited[0][0] = true

    while (q.isNotEmpty()) {
        val (row, col, brokenWallCount) = q.poll()

        for (i in 0 until 4) {
            val nextRow = row + dRow[i]
            val nextCol = col + dCol[i]
            if (nextRow !in maze.indices || nextCol !in maze[0].indices || visited[nextRow][nextCol]) {
                continue
            }
            if (nextRow == maze.lastIndex && nextCol == maze[0].lastIndex) {
                return brokenWallCount
            }

            visited[nextRow][nextCol] = true
            // 부순 벽의 개수가 0인 상태의 이동이 다 끝나면, 1인 상태의 방문을, 그것도 다 끝나면 2, 3... 순으로 진행된다
            // 우선순위 큐를 사용하는 대신 count가 증가하면 큐의 뒤에 삽입한다
            if (maze[nextRow][nextCol] == '0') {
                q.addFirst(State(nextRow, nextCol, brokenWallCount))
            } else if (maze[nextRow][nextCol] == '1') {
                q.addLast(State(nextRow, nextCol, brokenWallCount + 1))
            }
        }
    }

    return 0
}