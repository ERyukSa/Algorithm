/**
 * https://www.acmicpc.net/problem/5052
 * 1차 시도: 왼쪽부터 각 자리 작은 값을 가진 번호가 먼저 오도록 정렬 후 인접 번호끼리만 비교 => 10% fail
 * 2차 시도: 문자열 기본 기준 정렬 + 인접한 번호끼리만 접두어 검사 => success
 *  -> 문자열 정렬의 기본 기준은 (1)알파벳 and (2)길이
 *
 * 문자열 기본 정렬을 수행하면 번호마다 한 번의 비교로 자신이 접두어인지 여부를 확인할 수 있다.
 * Why? 문자열 기본 정렬 방식과 접두어 비교 방식이 완전히 같기 때문이다: 왼쪽부터 각 문자를 아스키 코드 기준으로 비교함
 */
fun main() {
    val t = readln().toInt()
    val answer = StringBuilder()

    repeat(t) {
        val numberCount = readln().toInt()
        val numbers = Array(numberCount) { readln() }
        numbers.sort()

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
        if (numbers[i].length > numbers[i + 1].length) continue

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