/**
 * 백준 1509
 * https://www.acmicpc.net/problem/1509
 */
private lateinit var dp: Array<IntArray>

fun main() {
    val string = readln()
    dp = Array(string.length) {
        IntArray(string.length) { -1 }
    }
    print(getMinPartitionCount(string, 0, string.lastIndex))
}

private fun getMinPartitionCount(string: String, start: Int, end: Int): Int {
    if (dp[start][end] != -1) {
        return dp[start][end]
    }

    if (start == end || isPalindrom(string, start, end)) {
        return 1
    }

    var minCount = Int.MAX_VALUE
    for (leftLength in 1..(end - start)) {
        if (isPalindrom(string, start, start + leftLength - 1)) {
            minCount = minOf(minCount, 1 + getMinPartitionCount(string, start + leftLength, end))
        }
    }

    dp[start][end] = minCount
    return minCount
}

private fun isPalindrom(string: String, start: Int, end: Int): Boolean {
    var left = start
    var right = end

    while (left < right) {
        if (string[left++] != string[right--]) {
            return false
        }
    }

    return true
}