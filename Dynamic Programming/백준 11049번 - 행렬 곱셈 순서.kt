/**
 * https://www.acmicpc.net/problem/11049
 * 1차,2차: 1% fail, 3차: success
 * 작은 문제의 결과로 큰 문제를 해결한다는 DP 기본 원리에 충실하자
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
    if (minTimesCount[start][end] != -1) {
        return minTimesCount[start][end]
    }

    var myMinTimesCount = Int.MAX_VALUE
    for (separator in start until end) {
        val leftMinCount = calculateMinTimesCount(start, separator)
        val rightMinCount = calculateMinTimesCount(separator + 1, end)
        val adjacentCount = matrices[start].rowSize * matrices[separator].colSize * matrices[end].colSize
        myMinTimesCount = minOf(myMinTimesCount, leftMinCount + adjacentCount + rightMinCount)
    }

    minTimesCount[start][end] = myMinTimesCount
    return myMinTimesCount
}