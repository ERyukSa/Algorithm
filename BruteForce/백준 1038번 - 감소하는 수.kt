/**
 * https://www.acmicpc.net/problem/1038
 * 1. 백트래킹으로 0 ~ 9876543210까지 감소하는 수를 구한다
 * 2. 정렬한다
 * 3. n번째 값을 출력한다
 * 4. 숫자 범위에 주의한다
 */

import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.pow

val decreasingNumbers = mutableListOf<Long>()
    
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val targetIndex = readLine().toInt()

    if (targetIndex <= 10) {
        println(targetIndex)
    } else {
        addDecreasingNumber(0, 0, 0)
        decreasingNumbers.sort()

        if (targetIndex > decreasingNumbers.lastIndex) {
            println(-1)
        } else {
            println(decreasingNumbers[targetIndex])
        }
    }
}

fun addDecreasingNumber(startNumber: Int, beforeLength: Int, beforeNumber: Long) {
    if (beforeLength >= 10 || startNumber >= 10) {
        return
    }

    for (i in startNumber..9) {
        val newNumber = i * (10.0).pow(beforeLength).toLong() + beforeNumber
        decreasingNumbers.add(newNumber)
        addDecreasingNumber(i + 1, beforeLength + 1, newNumber)
    }
}