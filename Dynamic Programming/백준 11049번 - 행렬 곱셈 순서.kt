/**
 * https://www.acmicpc.net/problem/11049
 * DP, 1차: 1% fail
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

    calculateMinTimesCount(0, matrixCount - 1).let { print(it) }
}

private fun calculateMinTimesCount(start: Int, end: Int): Int {
    if (start == end) {
        return 0
    }
    if (start + 1 == end) {
        return matrices[start].rowSize * matrices[start].colSize * matrices[end].colSize
    }
    if (minTimesCount[start][end] != -1) {
        return minTimesCount[start][end]
    }

    var myMinTimesCount = Int.MAX_VALUE
    for (i in start + 1 until end) {
        val leftMinTimesCount = calculateMinTimesCount(start, i - 1)
        val rightMinTimesCount = calculateMinTimesCount(i + 1, end)
        val leftFirstTimesCount = (matrices.first().rowSize * matrices[i].rowSize * matrices[i].colSize) +
                matrices.first().rowSize * matrices[i].colSize * matrices.last().colSize
        val rightFirstTimesCount = (matrices[i].rowSize * matrices[i].colSize * matrices.last().colSize) +
                matrices.first().rowSize * matrices[i].rowSize * matrices.last().colSize
        myMinTimesCount = minOf(
            myMinTimesCount,
            leftMinTimesCount + minOf(leftFirstTimesCount, rightFirstTimesCount) + rightMinTimesCount
        )
    }

    minTimesCount[start][end] = myMinTimesCount
    return myMinTimesCount
}