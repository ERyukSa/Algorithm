/* https://www.acmicpc.net/problem/1806 */
/* 투 포인터 */

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.math.min

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val (n, s) = readLine().split(" ").map(String::toInt)
    val numbers = with(StringTokenizer(readLine())) {
        IntArray(n) { this.nextToken().toInt() }
    }

    // 모든 수는 자연수, 부분 합은 연속된 '수'라서 길이가 1 이상이어야 함
    // 아무거나 하나만 선택해도 부분합이 1 이상이므로 1 출력 
    if (s <= 1) {
        println(1)
        return
    }

    var answer = n + 1 // 부분 합의 최대 길이는 n이다
    var start = 0
    var end = 0
    var partSum = 0

    // [start, end): end가 가리키던 수를 partSum에 더하고 end를 증가시키는 구조
    while(true) {
        if (end >= n && partSum < s) break

        if (partSum < s) {
            partSum += numbers[end++]
        } else {
            answer = min(answer, end - start)
            partSum -= numbers[start++]
        }
    }

    if (answer == n + 1) {
        answer = 0
    }
    println(answer)
}