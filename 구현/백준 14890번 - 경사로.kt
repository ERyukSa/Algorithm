/**
 * https://www.acmicpc.net/problem/14890
 * 빡구현, 고려해야 할 요소가 많다
 */

private enum class Direction {
    UP, DOWN, LEFT, RIGHT
}

private lateinit var levelBoard: Array<IntArray>
private var slopeSize = 0

fun main() {
    val (boardSize, _slopeSize) = readln().split(" ").map(String::toInt)
    slopeSize = _slopeSize
    levelBoard = Array(boardSize) {
        readln().split(" ").map(String::toInt).toIntArray()
    }

    var bridgeCount = countRowBridge()
    bridgeCount += countColumnBridge()
    print(bridgeCount)
}

private fun countRowBridge(): Int {
    var bridgeCount = 0

    for (row in levelBoard.indices) {
        var prevLevel = levelBoard[row][0]
        var col = 1
        var slopIsPlaced = false

        while (col < levelBoard[0].size) {
            if (levelBoard[row][col] > prevLevel) {
                if (slopIsPlaced || canPlaceSlope(row, col - 1, Direction.LEFT).not()) {
                    break
                }
                prevLevel = levelBoard[row][col]
                col += 1
                slopIsPlaced = true
            } else if (levelBoard[row][col] < prevLevel) {
                if (canPlaceSlope(row, col, Direction.RIGHT).not()) {
                    break
                }
                prevLevel = levelBoard[row][col]
                col += slopeSize
                slopIsPlaced = true
            } else {
                col += 1
                slopIsPlaced = false
            }
        }

        if (col == levelBoard[0].size) {
            bridgeCount += 1
        }
    }

    return bridgeCount
}

private fun countColumnBridge(): Int {
    var bridgeCount = 0

    for (col in levelBoard[0].indices) {
        var prevLevel = levelBoard[0][col]
        var row = 1
        var slopIsPlaced = false

        while (row < levelBoard.size) {
            if (levelBoard[row][col] > prevLevel) {
                if (slopIsPlaced || canPlaceSlope(row - 1, col, Direction.UP).not()) {
                    break
                }
                prevLevel = levelBoard[row][col]
                row += 1
                slopIsPlaced = true
            } else if (levelBoard[row][col] < prevLevel) {
                if (canPlaceSlope(row, col, Direction.DOWN).not()) {
                    break
                }
                prevLevel = levelBoard[row][col]
                row += slopeSize
                slopIsPlaced = true
            } else {
                row += 1
                slopIsPlaced = false
            }
        }

        if (row == levelBoard.size) {
            bridgeCount += 1
        }
    }

    return bridgeCount
}

private fun canPlaceSlope(row: Int, col: Int, dir: Direction): Boolean {
    val level = levelBoard[row][col]

    return when (dir) {
        Direction.LEFT -> {
            if (col - slopeSize + 1 < 0) return false
            for (i in col - 1 downTo (col - slopeSize + 1)) {
                if (levelBoard[row][i] != level) return false
            }
            true
        }
        Direction.RIGHT -> {
            if (col + slopeSize - 1 >= levelBoard.size) return false
            for (i in col + 1 until col + slopeSize) {
                if (levelBoard[row][i] != level) return false
            }
            true
        }
        Direction.UP -> {
            if (row - slopeSize + 1 < 0) return false
            for (i in row - 1 downTo (row - slopeSize + 1)) {
                if (levelBoard[i][col] != level) return false
            }
            true
        }
        Direction.DOWN -> {
            if (row + slopeSize - 1 >= levelBoard.size) return false
            for (i in row + 1 until row + slopeSize) {
                if (levelBoard[i][col] != level) return false
            }
            true
        }
    }
}