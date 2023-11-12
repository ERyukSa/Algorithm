/**
 * 단기간 성장
 * https://www.acmicpc.net/workbook/view/4349
 */

/**
 * https://www.acmicpc.net/problem/10942 펠린드롬?
 * 유형: DP
 */

fun main() {
    val n = readln().toInt()
    val numbers = readln().split(" ").map(String::toInt)
    val isPalindrome = Array(n) { BooleanArray(n) } // [start][end]: numbers[start,end]가 펠린드롬인가?
    val sb = StringBuilder()

    // 길이 1일 때 초기화
    for (i in 0 until n) {
        isPalindrome[i][i] = true
    }

    // 길이 2일 때 초기화
    for (i in 0 until n - 1) {
        if (numbers[i] == numbers[i + 1]) {
            isPalindrome[i][i + 1] = true
        }
    }

    for (length in 3..n) {
        for (start in 0 .. n - length) {
            val end = start + length - 1
            // numbers[start]와 numbers[end]가 같고, numbers[start+1,end-1]가 펠린드롬이면
            if (numbers[start] == numbers[end] && isPalindrome[start + 1][end - 1]) {
                isPalindrome[start][end] = true
            }
        }
    }

    repeat(readln().toInt()) {
        val (start, end) = readln().split(" ").map(String::toInt)
        if (isPalindrome[start - 1][end - 1]) {
            sb.append(1)
        } else {
            sb.append(0)
        }
        sb.appendLine()
    }

    print(sb.toString())
}