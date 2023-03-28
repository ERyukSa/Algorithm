/**
 * https://www.acmicpc.net/problem/17136
 * 완전 탐색, 구현 어려웠음
 */

private lateinit var graph: Array<IntArray>
private var initialOneCount = 0
private val paperCount = intArrayOf(0, 5, 5, 5, 5, 5)
private var minUsedPaperCount = Int.MAX_VALUE

fun main() {
    graph = Array(10) {
        readln().split(" ").map(String::toInt).toIntArray()
    }

    tryAllCase(0, 0)

    if (minUsedPaperCount == Int.MAX_VALUE) {
        print(-1)
    } else {
        print(minUsedPaperCount)
    }
}

/**
 * 풀이1: 재귀 하나당 한 점을 의미, 1이면 모든 경우 시도, 0이면 다음 점으로 넘어간다
 */
private fun tryAllCase(startRow: Int, startColumn: Int, usedPaperCount: Int, coveredCount: Int) {
    if (coveredCount == initialOneCount) {
        minUsedPaperCount = minOf(minUsedPaperCount, usedPaperCount)
        return
    }
    if (startRow == 10 || startColumn == 10) {
        return
    }
    if (usedPaperCount == 25) {
        return
    }

    val nextRow = if (startColumn == 9) startRow + 1 else startRow
    val nextColumn = if (startColumn == 9) 0 else startColumn + 1

    if (graph[startRow][startColumn] == 1) {
        for (size in 1..5) {
            if (paperCount[size] == 0) continue
            if (startRow + size > 10 || startColumn + size > 10) break

            if (canOverlay(size, startRow, startColumn)) {
                overlay(size, startRow, startColumn)
                paperCount[size] -= 1
                tryAllCase(nextRow, nextColumn, usedPaperCount + 1, coveredCount + size * size)
                paperCount[size] += 1
                undo(size, startRow, startColumn)
            }
        }
    } else {
        tryAllCase(nextRow, nextColumn, usedPaperCount, coveredCount)
    }
}

/**
 * 풀이2: 재귀 구현 다름 - 내부에서 반복문으로 1찾고, 전부 붙여보기
 */
private fun tryAllCase2(startRow: Int, usedPaperCount: Int) {
    var foundRow = 0
    var foundColumn = -1

    for (row in startRow until 10) {
        for (column in 0 until 10) {
            if (graph[row][column] == 1) {
                foundRow = row
                foundColumn = column
                break
            }
        }
        if (foundColumn != -1) break
    }

    if (foundColumn == -1) {
        minUsedPaperCount = minOf(minUsedPaperCount, usedPaperCount)
        return
    }
    if (usedPaperCount == 25 || usedPaperCount >= minUsedPaperCount) {
        return
    }

    for (size in 1..5) {
        if (paperCount[size] == 0) continue
        if (canOverlay(size, foundRow, foundColumn)) {
            overlay(size, foundRow, foundColumn)
            paperCount[size] -= 1
            tryAllCase(foundRow, usedPaperCount + 1)
            paperCount[size] += 1
            undo(size, foundRow, foundColumn)
        }
    }
}

private fun overlay(squareSize: Int, startRow: Int, startColumn: Int) {
    for (row in startRow until startRow + squareSize) {
        for (column in startColumn until startColumn + squareSize) {
            graph[row][column] = 0
        }
    }
}

private fun undo(squareSize: Int, startRow: Int, startColumn: Int) {
    for (row in startRow until startRow + squareSize) {
        for (column in startColumn until startColumn + squareSize) {
            graph[row][column] = 1
        }
    }
}

private fun canOverlay(squareSize: Int, startRow: Int, startColumn: Int): Boolean {
    if (startRow + squareSize > 10 || startColumn + squareSize > 10) {
        return false
    }
    for (row in startRow until startRow + squareSize) {
        for (column in startColumn until startColumn + squareSize) {
            if (graph[row][column] == 0) {
                return false
            }
        }
    }
    return true
}