/* https://www.acmicpc.net/problem/17070
   좀 무식하게 풀었어서 최적화 필요
 */

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

data class Coord(var row: Int, var col: Int)

lateinit var graph: Array<IntArray>

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val size = readLine().toInt()
    graph = Array(size) {
        readLine().split(" ").map(String::toInt).toIntArray()
    }

    val pipe = LinkedList(listOf(Coord(0, 0), Coord(0, 1)))
    println(getCaseCount(0, pipe))
}

/**
 * 0: 가로, 1: 세로, 2: 대각
 */
fun getCaseCount(direction: Int, pipe: LinkedList<Coord>): Int {
    if (pipe[1].row == graph.lastIndex && pipe[1].col == graph.lastIndex) {
        return 1
    }

    // 앞쪽 파이프 칸을 뒤로 옮긴다
    val front = pipe.removeFirst()
    pipe.addLast(front)

    val currentFrontRow = front.row
    val currentFrontCol = front.col
    var count = 0

    when(direction) {
        0 -> {
            // 오른쪽 -> 오른쪽
            front.col += 2
            if (front.row in graph.indices && front.col in graph.indices && graph[front.row][front.col] == 0){
                count += getCaseCount(0, pipe)
            }

            // 오른쪽 -> 대각선
            front.row += 1
            if (front.row in graph.indices && front.col in graph.indices && graph[front.row][front.col] == 0 && graph[front.row-1][front.col] == 0 && graph[front.row][front.col-1] == 0){
                count += getCaseCount(2, pipe)
            }
        }
        1 -> {
            // 아래 -> 아래ㅌ
            front.row += 2
            if (front.row in graph.indices && front.col in graph.indices && graph[front.row][front.col] == 0){
                count += getCaseCount(1, pipe)
            }

            // 아래 -> 대각선
            front.col += 1
            if (front.row in graph.indices && front.col in graph.indices && graph[front.row][front.col] == 0 && graph[front.row-1][front.col] == 0 && graph[front.row][front.col-1] == 0){
                count += getCaseCount(2, pipe)
            }
        }
        else -> {
            // 대각 -> 오른쪽
            front.row += 1
            front.col += 2
            if (front.row in graph.indices && front.col in graph.indices && graph[front.row][front.col] == 0){
                count += getCaseCount(0, pipe)
            }

            // 대각 -> 대각
            front.row += 1
            if (front.row in graph.indices && front.col in graph.indices && graph[front.row][front.col] == 0 && graph[front.row-1][front.col] == 0 && graph[front.row][front.col-1] == 0){
                count += getCaseCount(2, pipe)
            }

            // 대각 -> 아래
            front.col -= 1
            if (front.row in graph.indices && front.col in graph.indices && graph[front.row][front.col] == 0){
                count += getCaseCount(1, pipe)
            }
        }
    }

    // 파이프 호출했을 때로 되돌리기
    undoPipe(pipe, currentFrontRow, currentFrontCol)
    return count
}

fun undoPipe(pipe: LinkedList<Coord>, originalRow: Int, originalCol: Int) {
    pipe.addFirst(pipe.removeLast())
    pipe.peekFirst().apply {
        row = originalRow
        col = originalCol
    }
}