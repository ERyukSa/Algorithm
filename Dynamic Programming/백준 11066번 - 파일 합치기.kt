/**
 * 단기간 성장
 * https://www.acmicpc.net/workbook/view/4349
 */

/**
 * https://www.acmicpc.net/problem/11066 파일합치기
 * 유형: DP
 */

lateinit var fileCost: IntArray
lateinit var accCostSum: IntArray
lateinit var minCostCache: Array<IntArray>

fun main() {
    val sb = StringBuilder()

    repeat(readln().toInt()) {
        val fileCount = readln().toInt()
        fileCost = readln().split(" ").map(String::toInt).toIntArray()
        accCostSum = IntArray(fileCount)
        minCostCache = Array(fileCount) { IntArray(fileCount) { -1 } }

        accCostSum[0] = fileCost[0]
        for (i in 1 until fileCount) {
            accCostSum[i] = accCostSum[i - 1] + fileCost[i]
        }

        sb.append(calculateMinCost(0, fileCount - 1)).appendLine()
    }

    print(sb.toString())
}

fun calculateMinCost(start: Int, end: Int): Int {
    if (minCostCache[start][end] != -1) {
        return minCostCache[start][end]
    }

    if (start == end) {
        return 0
    }

    var minCost = Int.MAX_VALUE
    for (mid in start until end) {
        val partSum1 = if (start == 0) accCostSum[mid] else accCostSum[mid] - accCostSum[start - 1]
        val partSum2 = accCostSum[end] - accCostSum[mid]

        minCost = minOf(
            minCost,
            calculateMinCost(start, mid) + calculateMinCost(mid + 1, end) + partSum1 + partSum2
        )
    }

    minCostCache[start][end] = minCost
    return minCost
}