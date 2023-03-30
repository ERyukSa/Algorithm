/**
 * https://www.acmicpc.net/problem/2407
 * DP, 큰 수 연산, 수학
 */
import java.math.BigInteger

private lateinit var combCountTable: Array<Array<BigInteger>>

fun main() {
    val (n, m) = readln().split(" ").map(String::toInt)
    combCountTable = Array(n + 1) {
        Array(m + 1) { BigInteger.ONE }
    }

    for (i in 2..n) {
        combCountTable[i][1] = BigInteger.valueOf(i.toLong())
    }

    for (i in 2..n) {
        for (j in 2.. (i - 1).coerceAtMost(m)) {
            combCountTable[i][j] =
                combCountTable[i - 1][j] + combCountTable[i - 1][j - 1]
        }
    }

    print(combCountTable[n][m])
}
