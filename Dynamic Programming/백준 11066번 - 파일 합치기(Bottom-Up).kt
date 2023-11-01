/**
 * 단기간 성장
 * https://www.acmicpc.net/workbook/view/4349
 */

/**
 * https://www.acmicpc.net/problem/11066 파일합치기
 * 유형: DP (Bottom-Up)
 */

fun main() {
    val sb = StringBuilder()

    repeat(readln().toInt()) {
        val fileCount = readln().toInt()
        val fileCost = (listOf(-1) + readln().split(" ").map(String::toInt)).toIntArray()
        val accCostSum = IntArray(fileCount + 1)
        val minCostCache = Array(fileCount + 1) { IntArray(fileCount + 1) { Int.MAX_VALUE } }

        for (i in 1 ..fileCount) {
            accCostSum[i] = accCostSum[i - 1] + fileCost[i]
        }
        for (i in 1..fileCount) {
            minCostCache[i][i] = 0
        }

        for (extraLength in 1 until fileCount) {
            for (start in 1..fileCount - extraLength) {
                val end = start + extraLength
                for (pivot in start until end) {
                    minCostCache[start][end] = minOf(
                        minCostCache[start][end],
                        minCostCache[start][pivot] + minCostCache[pivot + 1][end] + accCostSum[end] - accCostSum[start - 1]
                    )
                }
            }
        }

        sb.append(minCostCache[1][fileCount]).appendLine()
    }

    print(sb.toString())
}