/**
 * https://www.acmicpc.net/problem/2339
 * [분할 정복] 기반의 겁나게 빡센 구현 문제
 */
enum class DIR {
    ROW, COLUMN
}
private fun DIR.getAnother(): DIR = when(this) {
    DIR.ROW -> DIR.COLUMN
    DIR.COLUMN -> DIR.ROW
}

private lateinit var originBoard: Array<IntArray>
private lateinit var tempBoard: Array<IntArray>

// 0: 빈곳, 1: 불순물, 2: 보석
fun main() {
    val boardSize = readln().toInt()
    originBoard = Array(boardSize) { readln().split(" ").map(String::toInt).toIntArray() }
    tempBoard = Array(boardSize) { row ->
        IntArray(boardSize) { col ->
            originBoard[row][col]
        }
    }

    var count = getCount(0, 0, boardSize - 1, boardSize - 1, DIR.COLUMN)
    count += getCount(0, 0, boardSize - 1, boardSize - 1, DIR.ROW)

    if (count == 0) print(-1) else print(count)
}

// 좌표 영역을 나누는 가지수 반환,
private fun getCount(r1: Int, c1: Int, r2: Int, c2: Int, dir: DIR): Int {
    var dirty = false
    var count = 0

    // 가로 세로 나눠서 불순물마다 한 줄씩만 잘라본다
    when (dir) {
        DIR.ROW -> {
            for (row in r1 + 1 until r2) {
                for (col in c1..c2) {
                    if (tempBoard[row][col] == 1) {
                        dirty = true
                        var tempCount = 0

                        if (cutBoard(r1, c1, r2, c2, row, col, dir)) {
                            // (r3, c3): 위쪽 영역의 오른쪽 아래 좌표, (r4, c4): 아래 영역의 왼쪽 위 좌표
                            val r3 = row - 1
                            val c3 = c2
                            val r4 = row + 1
                            val c4 = c1

                            tempCount = getCount(r1, c1, r3, c3, dir.getAnother())
                            if (tempCount > 0) { // 위쪽 영역에 올바르게 잘린 석판이 없었다면 확인하지 않는다
                                tempCount *= getCount(r4, c4, r2, c2, dir.getAnother())
                            }
                        }

                        count += tempCount
                        resetTempBoard(r1, c1, r2, c2) // 다른 행을 확인하기 위해 잘랐던 부분 되돌리기
                        break
                    }
                }
            }
        }

        DIR.COLUMN -> {
            for (col in c1 + 1 until c2) {
                for (row in r1..r2) {
                    if (tempBoard[row][col] == 1) {
                        dirty = true
                        var tempCount = 0

                        if (cutBoard(r1, c1, r2, c2, row, col, dir)) {
                            val r3 = r2
                            val c3 = col - 1
                            val r4 = r1
                            val c4 = col + 1

                            tempCount = getCount(r1, c1, r3, c3, dir.getAnother())
                            if (tempCount > 0) {
                                tempCount *= getCount(r4, c4, r2, c2, dir.getAnother())
                            }
                        }

                        count += tempCount
                        resetTempBoard(r1, c1, r2, c2)
                        break
                    }
                }
            }
        }
    }

    // 영역에 불순문이 있었다면 가지수를 반환하고
    // 없었다면 지금 영역이 불순문 없이 보석 하나만 갖도록 잘렸는지 확인한다
    return if (dirty) {
        count
    } else {
        return if (isGoodBoard(r1, c1, r2, c2)) 1 else 0
    }
}

private fun resetTempBoard(r1: Int, c1: Int, r2: Int, c2: Int) {
    for (row in r1..r2) {
        for (col in c1..c2) {
            tempBoard[row][col] = originBoard[row][col]
        }
    }
}

private fun cutBoard(r1: Int, c1: Int, r2: Int, c2: Int, spotRow: Int, spotCol: Int, dir: DIR): Boolean {
    when (dir) {
        DIR.ROW -> {
            for (col in c1..c2) {
                if (tempBoard[spotRow][col] == 2) return false
                tempBoard[spotRow][col] = 1
            }
        }
        DIR.COLUMN -> {
            for (row in r1..r2) {
                if (tempBoard[row][spotCol] == 2) return false
                tempBoard[row][spotCol] = 1
            }
        }
    }

    return true
}

private fun isGoodBoard(r1: Int, c1: Int, r2: Int, c2: Int): Boolean {
    var count = 0
    for (row in r1..r2) {
        for (col in c1..c2) {
            if (tempBoard[row][col] == 1) return false
            if (tempBoard[row][col] == 2) count++
        }
    }

    return count == 1
}