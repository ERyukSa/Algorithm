/* https://programmers.co.kr/learn/courses/30/lessons/60058 */

import java.util.*

class Solution {
    fun solution(p: String): String {
        val answer = getRightOne(p)
        return answer
    }
    
    fun getRightOne(input: String): String {
        if (input.isEmpty()) {
            return ""
        }
        
        var (brackets1, brackets2) = separate(input)
        brackets2 = getRightOne(brackets2)
        
        return if (isRight(brackets1)) {
            brackets1 + brackets2
        } else {
            StringBuilder()
                .append('(')
                .append(brackets2)
                .append(')')
                .append(getReversed(brackets1))
                .toString()
        }
    }
    
    // 앞, 끝 문자는 삭제하고 괄호 방향을 뒤집어서 반환
    fun getReversed(brackets: String): String {
        val sb = StringBuilder()
        
        for (i in 1 until brackets.lastIndex) {
            if (brackets[i] == '(') {
                sb.append(')')
            } else {
                sb.append('(')
            }
        }
        
        return sb.toString()
    }
    
    // 처음으로 각 괄호의 개수가 맞을 때
    // 더이상 분리할 수 없는 균형잡힌 괄호가 된다
    fun separate(input: String): Pair<String, String> {
        var sum = 0
        var boundaryIdx = 0
        
        for (i in input.indices) {
            if(input[i] == '(') {
                sum++
            } else {
                sum --
            }
            
            if (sum == 0) {
                boundaryIdx = i + 1
                break
            }
        }
        
        return if (boundaryIdx == input.length) {
            Pair(input, "")
        } else {
            Pair(input.substring(0, boundaryIdx), input.substring(boundaryIdx))
        }
    }
    
    fun isRight(brackets: String): Boolean {
        var sum = 0
        
        brackets.forEach {
            if (it == '(') {
                sum++
            } else {
                sum--
            }
            
            if (sum < 0) {
                return false
            }
        }
        
        return true
    }
}