/* https://leetcode.com/problems/longest-common-prefix/ */

/**
 * 유형) 문자열 처리, 구현
 * 풀이)
 * 1. 모든 문자열을 앞에서부터 한 글자씩 검사한다.
 * 2-1. 모두 같은 글자를 갖고 있으면, 정답에 글자를 추가하고 다음 인덱스로 넘어간다.
 * 2-2. 하나라도 글자가 다르면, 검사를 중단한다.
 *
 * 피드백) 구현할 때 막힌다 -> 설명을 해본다 -> 그 과정에서 스스로 정리가 된다
 */
class Solution {
    fun longestCommonPrefix(input: Array<String>): String {
        val answer = StringBuilder()
        var minLength = Int.MAX_VALUE
        
        // 최소 길이를 찾는다
        for (str in input) {
            minLength = str.length.coerceAtMost(minLength)
        }

        for (prefixIndex in 0 until minLength) { // 글자 인덱스
            var isAllSame = true

            // 붙어있는 문자열끼리 prefixIndex에 같은 문자를 갖고 있는지 비교한다
            for (i in 0 until input.lastIndex) {
                val prevStr = input[i]
                val currentStr = input[i + 1]

                // 하나라도 다르면 검사를 중단한다
                if (prevStr[prefixIndex] != currentStr[prefixIndex]) {
                    isAllSame = false
                    break
                }
            }

            // 문자가 일치하면 정답에 추가, 다르면 검사 중단
            if (isAllSame) {
                answer.append(input[0][prefixIndex])
            } else {
                break
            }
        }

        return answer.toString()
    }
}