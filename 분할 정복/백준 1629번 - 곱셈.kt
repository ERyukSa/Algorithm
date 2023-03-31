/**
 * https://www.acmicpc.net/problem/1629
 * 수학, 분할 정복
 */
private var remainBoundary = 0L

fun main() {
    val (a, b, c) = readln().split(" ").map(String::toLong)
    remainBoundary = c
    print(power(a, b.toInt()))
}

private fun power(number: Long, recursive: Int): Long {
    if (recursive == 1) {
        return number % remainBoundary
    }

    val halfPower = power(number, recursive / 2)
    return if (recursive % 2 == 0) {
        (halfPower * halfPower) % remainBoundary
    } else {
        (halfPower * halfPower) % remainBoundary * number % remainBoundary
    }
}
