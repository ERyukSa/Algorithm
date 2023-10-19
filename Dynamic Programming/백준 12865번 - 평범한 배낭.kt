/**
 * 단기간 성장
 * https://www.acmicpc.net/workbook/view/4349
 */

/**
 * https://www.acmicpc.net/problem/12865 평범한 배낭
 * 유형: DP
 * 재귀 함수로 해결했다. 오랜만에 푸니까 어려웠다. 풀긴 했는데 설명을 못하겠다. 복습해야겠다.
 */

data class Stuff(val weight: Int, val value: Int)

lateinit var stuffArray: Array<Stuff>
lateinit var maxValueSumCache: Array<IntArray>

fun main() {
    val (stuffCount, weightLimit) = readln().split(" ").map(String::toInt)
    stuffArray = Array(stuffCount) {
        readln().split(" ").run {
            Stuff(weight = this[0].toInt(), value = this[1].toInt())
        }
    }
    maxValueSumCache = Array(weightLimit + 1) {
        IntArray(stuffCount) { -1 }
    }

    calculateMaxValueSum(weightLimit, 0).let {
        println(it)
    }
}

fun calculateMaxValueSum(weightLimit: Int, stuffIndex: Int): Int {
    if (weightLimit <= 0 || stuffIndex >= stuffArray.size) {
        return 0
    }
    if (maxValueSumCache[weightLimit][stuffIndex] != -1) {
        return maxValueSumCache[weightLimit][stuffIndex]
    }

    val stuff = stuffArray[stuffIndex]

    maxValueSumCache[weightLimit][stuffIndex] = calculateMaxValueSum(weightLimit, stuffIndex + 1)
    if (weightLimit >= stuff.weight) {
        maxValueSumCache[weightLimit][stuffIndex] = maxOf(
            maxValueSumCache[weightLimit][stuffIndex],
            stuff.value + calculateMaxValueSum(weightLimit - stuff.weight, stuffIndex + 1)
        )
    }

    return maxValueSumCache[weightLimit][stuffIndex]
}