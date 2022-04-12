/* https://programmers.co.kr/learn/courses/30/lessons/64065 */
/* 문자열 파싱 + Map or Set 사용 */

class Solution {
    fun solution(s: String): IntArray {
        val countArray = IntArray(100_001)
        val parsedStr = s.removePrefix("{{").removeSuffix("}}").split("},{")
        val answer = IntArray(parsedStr.size)

        for (i in parsedStr.indices) {
            val lastParsedStr = parsedStr[i].split(",")
            for (strNum in lastParsedStr) {
                countArray[strNum.toInt()]++
            }
        }

        for (num in 1 until countArray.size) {
            val count = countArray[num]
            if (count > 0) answer[answer.size - count] = num
        }

        return answer
    }
}