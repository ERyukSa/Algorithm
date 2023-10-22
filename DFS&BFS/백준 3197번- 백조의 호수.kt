/**
 * 단기간 성장
 * https://www.acmicpc.net/workbook/view/4349
 */

/**
 * https://www.acmicpc.net/problem/3197 백조의 호수
 * 유형: DFS, BFS, 분리집합
 * 해결: BFS 응용
 * 풀이:
 * 백조 찾기(BFS) + 얼음 녹이기로 알고리즘이 진행된다.
 * 1초마다 백조 찾기와 얼음 녹이기를 매번 새로 수행할 경우 시간 내에 문제를 해결할 수 없다.
 * - 백조 찾기: 백조를 찾을(만남) 때는 한 쪽 백조에서 BFS를 진행하되 물을 만날 경우 큐에 넣고, 얼음을 만날 경우 다음에 사용할 예비 큐에
 * 저장한다. 그러면 알고리즘 동안 BFS를 맵 전체에 대해 한 번만 수행할 수 있게 된다.
 * - 얼음 녹이기: 현재 물이 된 부분만 큐에 저장한다. 얼음을 녹일 때는 현재 큐에 저장된 만큼만 수행한다.
 * 그리고 방금 녹여서 물이 된 얼음의 위치를 큐의 뒤에 삽입하여 다음 얼음 녹이기에 사용하면, 매번 맵 전체에서 물을 찾아서 얼음을 녹이는
 * 방법보다 빠르게 수행할 수 있다.
 */
import java.util.LinkedList

data class Location(val row: Int, val col: Int)

lateinit var lake: Array<CharArray>
lateinit var visited: Array<BooleanArray>
val waterQueue = LinkedList<Location>()
val swanQueue = LinkedList<Location>()
val failedSwanQueue = LinkedList<Location>()
val dRow = intArrayOf(-1, 1, 0, 0)
val dCol = intArrayOf(0, 0, -1, 1)


fun mainSuccess() {
    val (rowSize, colSize) = readln().split(" ").map(String::toInt)
    lake = Array(rowSize) {
        readln().map { ch ->
            when (ch) {
                '.' -> 'W'
                'X' -> 'I'
                else -> 'S'
            }
        }.toCharArray()
    }
    visited = Array(rowSize) { BooleanArray(colSize) }
    var day = 0

    for (row in 0 until rowSize) {
        for (col in 0 until colSize) {
            if (lake[row][col] == 'W') {
                waterQueue.offer(Location(row, col))
            } else if (lake[row][col] == 'S') {
                waterQueue.offer(Location(row, col))
                if (swanQueue.isEmpty()) {
                    swanQueue.offer(Location(row, col))
                    visited[row][col] = true
                }
            }
        }
    }

    while (true) {
        if (canSwansMeet()) break
        meltIces()
        day++
    }

    print(day)
}

fun canSwansMeet(): Boolean {
    swanQueue.addAll(failedSwanQueue)
    failedSwanQueue.clear()

    while (swanQueue.isNotEmpty()) {
        val (row, col) = swanQueue.poll()

        for (i in 0 until 4) {
            val nextRow = row + dRow[i]
            val nextCol = col + dCol[i]

            if (nextRow !in lake.indices || nextCol !in lake[0].indices || visited[nextRow][nextCol]) continue

            when (lake[nextRow][nextCol]) {
                'S' -> return true
                'W' -> {
                    visited[nextRow][nextCol] = true
                    swanQueue.offer(Location(nextRow, nextCol))
                }
                else ->  {
                    visited[nextRow][nextCol] = true
                    failedSwanQueue.offer(Location(nextRow, nextCol))
                }
            }
        }
    }

    return false
}

fun meltIces() {
    val queueSize = waterQueue.size

    repeat(queueSize) {
        meltAround(waterQueue.poll())
    }
}

fun meltAround(waterLocation: Location) {
    val (row, col) = waterLocation

    for (i in 0 until 4) {
        val nextRow = row + dRow[i]
        val nextCol = col + dCol[i]

        if (nextRow in lake.indices && nextCol in lake[0].indices) {
            if (lake[nextRow][nextCol] == 'I') {
                lake[nextRow][nextCol] = 'W'
                waterQueue.offer(Location(nextRow, nextCol))
            }
        }
    }
}



/*
/**
 * https://www.acmicpc.net/problem/3197 백조의 호수
 * 유형: DFS, BFS, 분리집합
 * 해결 실패 (시간 초과), 시간: 1시간
 * 녹이기와 백조 찾기에 대한 그래프 탐색을 각각 한 번씩만 진행해서 문제를 해결할 수 있다고 한다.
 * 나는 하루 지날 때마다 그래프 전체를 탐색하고 있다. 행렬 사이즈가 커지면 여러번 돌아야 해서 시간이 초과되는 듯 하다.
 * 조금만 더 고민해봐야겠다.
 *//*



import java.util.LinkedList

data class Location(val row: Int, val col: Int)

lateinit var lake: Array<CharArray>
lateinit var visited: Array<BooleanArray>
val dRow = intArrayOf(-1, 1, 0, 0)
val dCol = intArrayOf(0, 0, -1, 1)


fun mainFail() {
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
}*/
