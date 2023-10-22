/**
 * 단기간 성장
 * https://www.acmicpc.net/workbook/view/4349
 */

/**
 * https://www.acmicpc.net/problem/12865 평범한 배낭
 * 유형: DP
 * (1) Top-Down
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


/**
 * (2) Bottom-Up
 */
/* data class Stuff(val weight: Int, val value: Int) */

fun mainBottomUp() {
    val (stuffCount, weightLimit) = readln().split(" ").map(String::toInt)
    val stuffArray: Array<Stuff> = Array(stuffCount) {
        readln().split(" ").run {
            Stuff(weight = this[0].toInt(), value = this[1].toInt())
        }
    }
    val maxValueSum: Array<IntArray> = Array(weightLimit + 1) {
        IntArray(stuffCount)
    }

    for (w in stuffArray[0].weight..weightLimit) {
        maxValueSum[w][0] = stuffArray[0].value
    }

    for (tempWeightLimit in 1..weightLimit) {
        for (i in 1 until stuffCount) {
            maxValueSum[tempWeightLimit][i] = maxValueSum[tempWeightLimit][i - 1]
            if (tempWeightLimit >= stuffArray[i].weight) {
                maxValueSum[tempWeightLimit][i] = maxOf(
                    maxValueSum[tempWeightLimit][i],
                    stuffArray[i].value + maxValueSum[tempWeightLimit - stuffArray[i].weight][i - 1]
                )
            }
        }
    }

    print(maxValueSum[weightLimit][stuffCount - 1])
}