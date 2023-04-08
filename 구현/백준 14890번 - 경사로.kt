/**
 * https://www.acmicpc.net/problem/14890
 * 빡구현, 고려해야 할 요소가 많다 (높낮이 차이=1, 경사로 겹침 여부, 앞에다 놓을지/뒤에 다놓을지, 각 라인을 독립적으로 계산)
 */

private enum class Direction {
    UP, DOWN, LEFT, RIGHT
}

private lateinit var levelBoard: Array<IntArray>
private lateinit var slopeIsPlace: Array<Array<BooleanArray>> // [놓는 방향][행][열]
private var slopeSize = 0

fun main() {
    val (boardSize, _slopeSize) = readln().split(" ").map(String::toInt)
    slopeSize = _slopeSize
    levelBoard = Array(boardSize) {
        readln().split(" ").map(String::toInt).toIntArray()
    }
    slopeIsPlace = Array(2) { Array(boardSize) { BooleanArray(boardSize) } }

    var bridgeCount = countRowBridge()
    bridgeCount += countColumnBridge()
    print(bridgeCount)
}

private fun countRowBridge(): Int {
    var bridgeCount = 0

    for (row in levelBoard.indices) {
        var prevLevel = levelBoard[row][0]
        var col = 1

        while (col < levelBoard[0].size) {
            if (levelBoard[row][col] > prevLevel) {
                if (canPlaceSlope(row, col - 1, Direction.LEFT).not()) {
                    break
                }
                prevLevel = levelBoard[row][col]
                col += 1
            } else if (levelBoard[row][col] < prevLevel) {
                if (canPlaceSlope(row, col, Direction.RIGHT).not()) {
                    break
                }
                prevLevel = levelBoard[row][col]
                col += slopeSize
            } else {
                col += 1
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

        while (row < levelBoard.size) {
            if (levelBoard[row][col] > prevLevel) {
                if (canPlaceSlope(row - 1, col, Direction.UP).not()) {
                    break
                }
                prevLevel = levelBoard[row][col]
                row += 1
            } else if (levelBoard[row][col] < prevLevel) {
                if (canPlaceSlope(row, col, Direction.DOWN).not()) {
                    break
                }
                prevLevel = levelBoard[row][col]
                row += slopeSize
            } else {
                row += 1
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
            if (col - slopeSize + 1 < 0 || levelBoard[row][col + 1] - level > 1) return false
            for (i in col downTo (col - slopeSize + 1)) {
                if (levelBoard[row][i] != level || slopeIsPlace[0][row][i]) return false
            }
            for (i in col downTo (col - slopeSize + 1)) {
                slopeIsPlace[0][row][i] = true
            }
            true
        }
        Direction.RIGHT -> {
            if (col + slopeSize - 1 >= levelBoard.size || levelBoard[row][col - 1] - level > 1) return false
            for (i in col + 1 until col + slopeSize) {
                if (levelBoard[row][i] != level) return false
            }
            for (i in col until col + slopeSize) {
                slopeIsPlace[0][row][i] = true
            }
            true
        }
        Direction.UP -> {
            if (row - slopeSize + 1 < 0 || levelBoard[row + 1][col] - level > 1) return false
            for (i in row downTo (row - slopeSize + 1)) {
                if (levelBoard[i][col] != level || slopeIsPlace[1][i][col]) return false
            }
            for (i in row downTo (row - slopeSize + 1)) {
                slopeIsPlace[1][i][col] = true
            }
            true
        }
        Direction.DOWN -> {
            if (row + slopeSize - 1 >= levelBoard.size || levelBoard[row - 1][col] - level > 1) return false
            for (i in row + 1 until row + slopeSize) {
                if (levelBoard[i][col] != level) return false
            }
            for (i in row until row + slopeSize) {
                slopeIsPlace[1][i][col] = true
            }
            true
        }
    }
}
