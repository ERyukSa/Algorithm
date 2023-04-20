package src.week6

/**
 * https://www.acmicpc.net/problem/2294
 * 1차) DP를 공부하자.....!! (틀림)
 * 2차) 계속 공부하자
 */

fun main() {
    val (n, goalValue) = readln().split(" ").map(String::toInt)
    val minCountFor = IntArray(goalValue + 1) { goalValue + 1 }.apply { this[0] = 0 }
    val coins: List<Int> = run {
        val coinSet = HashSet<Int>()
        repeat(n) {
            coinSet.add(readln().toInt())
        }
        coinSet.sorted()
    }

    for (coin in coins) {
        for (value in coin..goalValue) {
            minCountFor[value] = minOf(minCountFor[value], minCountFor[value - coin] + 1)
        }
    }

    if (minCountFor[goalValue] == goalValue + 1) {
        print(-1)
    } else {
        print(minCountFor[goalValue])
    }
}