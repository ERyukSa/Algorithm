/**
 * https://www.acmicpc.net/problem/1799
 * 완전탐색인데, 정말 다 찾아보면 시간 초과가 나고, 처음에 이동할 수 있는 곳과 없는 곳을 가지쳐야 한다.
 * 시간복잡도 줄이는 방법으로 다시 풀어볼 것
 */

private lateinit var board: Array<IntArray>
private var maxBishopCount = 0

fun main() {
    val boardSize = readln().toInt()
    board = Array(boardSize) {
        readln().split(" ").map(String::toInt).toIntArray()
    }
    simulatePutBishop(0, 0, 0)
    print(maxBishopCount)
}

private fun simulatePutBishop(startRow: Int, startCol: Int, bishopCount: Int) {
    var targetRow = -1
    var targetCol = -1

    for (row in startRow until board.size) {
        val currentStartCol = if (row == startRow) startCol else 0

        for (col in currentStartCol until board[0].size) {
            if (board[row][col] == 1) {
                targetRow = row
                targetCol = col
                break
            }
        }
        if (targetRow != -1) break
    }

    if (targetRow == -1) {
        maxBishopCount = maxOf(maxBishopCount, bishopCount)
        return
    }

    setBishop(targetRow, targetCol)
    if (targetCol == board[0].lastIndex) {
        simulatePutBishop(targetRow + 1, 0, bishopCount + 1)
        removeBishop(targetRow, targetCol)

        simulatePutBishop(targetRow + 1, 0, bishopCount)
    } else {
        simulatePutBishop(targetRow, targetCol + 1, bishopCount + 1)
        removeBishop(targetRow, targetCol)

        simulatePutBishop(targetRow, targetCol + 1, bishopCount)
    }
}

private fun setBishop(targetRow: Int, targetCol: Int) {
    setBishopPlaceable(targetRow, targetCol, placeable = false)
}

private fun removeBishop(targetRow: Int, targetCol: Int) {
    setBishopPlaceable(targetRow, targetCol, placeable = true)
}

private fun setBishopPlaceable(targetRow: Int, targetCol: Int, placeable: Boolean) {
    var row = targetRow
    var col = targetCol
    val placeableCount = if (placeable) 1 else -1

    while (row > 0 && col > 0) {
        row -= 1
        col -= 1
    }
    while (row < board.size && col < board.size) {
        board[row][col] += placeableCount
        row += 1
        col += 1
    }

    row = targetRow
    col = targetCol
    while (row > 0 && col < board[0].lastIndex) {
        row -= 1
        col += 1
    }
    while (row < board.size && col >= 0) {
        board[row][col] = +placeableCount
        row += 1
        col -= 1
    }
}