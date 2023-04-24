/**
 * https://www.acmicpc.net/problem/11049
 * DP, 1차, 2차: 1% fail
 */
data class Matrix(val rowSize:Int, val colSize: Int)

private lateinit var matrices: Array<Matrix>
private lateinit var minTimesCount: Array<IntArray>

fun main() {
    val matrixCount = readln().toInt()
    matrices = Array(matrixCount) {
        readln().split(" ").run {
            Matrix(rowSize = this[0].toInt(), colSize = this[1].toInt())
        }
    }

    minTimesCount = Array(matrixCount) {
        IntArray(matrixCount) { -1 }
    }
    for (i in 0 until matrixCount - 1) {
        minTimesCount[i][i + 1] = matrices[i].rowSize * matrices[i].colSize * matrices[i + 1].colSize
    }

    calculateMinTimesCount(0, matrixCount - 1).let { print(it) }
}

private fun calculateMinTimesCount(start: Int, end: Int): Int {
    if (start >= end) {
        return 0
    }
    if (minTimesCount[start][end] != -1) {
        return minTimesCount[start][end]
    }

    var myMinTimesCount = Int.MAX_VALUE
    for (i in start until end) {
        val leftMinCount = calculateMinTimesCount(start, i - 1)
        val rightMinCount = calculateMinTimesCount(i + 2, end)
        val adjacentCount = matrices[i].rowSize * matrices[i].colSize * matrices[i + 1].colSize

        myMinTimesCount = minOf(
            myMinTimesCount,
            leftMinCount + adjacentCount + rightMinCount + getThreeSequentialTimesCount(start, i, i + 1, end)
        )
    }

    minTimesCount[start][end] = myMinTimesCount
    return myMinTimesCount
}

private fun getThreeSequentialTimesCount(start: Int, mid: Int, end: Int): Int {
    return minOf(
        matrices[start].rowSize * matrices[start].colSize * matrices[mid].colSize +
                matrices[start].rowSize * matrices[mid].colSize * matrices[end].colSize,
        matrices[mid].rowSize * matrices[end].rowSize * matrices[end].colSize +
                matrices[start].rowSize * matrices[mid].colSize * matrices[end].colSize,
    )
}

private fun getThreeSequentialTimesCount(start: Int, mid1: Int, mid2:Int, end: Int): Int {
    return when {
        start == mid1 -> matrices[mid1].rowSize * matrices[mid2].colSize * matrices[end].colSize
        end == mid2 -> matrices[start].rowSize * matrices[mid1].rowSize * matrices[mid2].colSize
        else -> minOf(
            matrices[start].rowSize * matrices[mid1].rowSize * matrices[mid2].colSize +
                    matrices[start].rowSize * matrices[mid2].colSize * matrices[end].colSize,
            matrices[mid1].rowSize * matrices[mid2].colSize * matrices[end].colSize +
                    matrices[start].rowSize * matrices[mid1].rowSize * matrices[end].colSize,
        )
    }
}