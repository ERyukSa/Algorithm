/**
 * https://www.acmicpc.net/problem/2294
 * DP를 공부하자.......!!
 */

fun main() {
    val (n, k) = readln().split(" ").map(String::toInt)
    val dpTable = Array(n + 1) {
        IntArray(k + 1) { n + 1 }
    }
    val coinSet = HashSet<Int>()
    repeat(n) {
        coinSet.add(readln().toInt())
    }
    val coins = coinSet.toIntArray()

    for (coinCount in 1..n) {
        for (goalSum in 1..k) {
            dpTable[coinCount][goalSum] = dpTable[coinCount - 1][goalSum]
            if (goalSum < coins[coinCount - 1]) continue
            dpTable[coinCount][goalSum] =
                minOf(dpTable[coinCount][goalSum],dpTable[coinCount - 1][goalSum - coins[coinCount - 1]] + 1)
        }
    }

    if (dpTable[n][k] == n + 1) {
        print(-1)
    } else {
        print(dpTable[n][k])
    }
}