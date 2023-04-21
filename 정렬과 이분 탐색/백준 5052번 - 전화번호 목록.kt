/**
 * https://www.acmicpc.net/problem/5052
 * 1차 시도: 왼쪽부터 각 자리 작은 값을 가진 번호가 먼저 오도록 정렬 후 인접 번호끼리만 비교 => 10% fail
 */
import kotlin.math.pow

fun main() {
    val t = readln().toInt()
    val answer = StringBuilder()

    repeat(t) {
        val numberCount = readln().trim().toInt()
        val numbers = Array(numberCount) { readln().trim() }
        numbers.sortBy {
            var sum = 0.0
            for (i in it.indices) {
                sum += 10.0.pow(10 - i) * it[i].code
            }
            sum
        }

        if (hasPrefix(numbers)) {
            answer.append("NO\n")
        } else {
            answer.append("YES\n")
        }
    }

    print(answer)
}

private fun hasPrefix(numbers: Array<String>): Boolean {
    for (i in 0 until numbers.lastIndex) {
        var isPrefix = true
        for (j in numbers[i].indices) {
            if (numbers[i][j] != numbers[i + 1][j]) {
                isPrefix = false
                break
            }
        }

        if (isPrefix) return true
    }

    return false
}