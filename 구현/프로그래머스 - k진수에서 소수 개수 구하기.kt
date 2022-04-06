/* https://programmers.co.kr/learn/courses/30/lessons/92335 
   카카오 2022 공채 문제
   구현, 파싱, 소수
*/

import java.util.*
import kotlin.math.sqrt

class Solution {
    fun solution(n: Int, k: Int): Int {
        var answer: Int = 0
        val kNum: String = convertToK(n, k)
        
        for (num in kNum.split("0")) {
            if(isPrime(num)) answer++
        }

        return answer
    }

    fun convertToK(n: Int, k: Int): String {
        val list = LinkedList<Int>()
        var number = n

        while (number > 0) {
            list.addFirst(number % k)
            number /= k
        }

        return StringBuilder().apply {
            for (digit in list) {
                append(digit)
            }
        }.toString()
    }

    fun isPrime(number: String): Boolean {
        if (number.isNullOrEmpty() || number == "1") return false

        val num = number.toLong()
        for (i in 2..sqrt(num.toDouble()).toLong()) {
            if (num % i == 0L) return false
        }

        return true
    }
}