/* https://leetcode.com/problems/generate-parentheses/ */

/**
 * n 쌍의 괄호가 주어질 때, 올바른 괄호 조합을 구하라
 * 올바른 괄호란?
 * 1. 여는 괄호가 먼저 온다.
 * 2. 여닫는 괄호의 수가 같다.
 * 3. 괄호를 모두 사용한다.
 *
 * 처음은 항상 여는 괄호를 넣는다
 * 그다음은? 여는 괄호를 넣을지, 닫는 괄호를 넣을지 결정한다.
 * 매 칸마다 여는 괄호를 넣을 수 있는지, 닫는 괄호를 넣을 수 있는지를 알아야한다.
 *
 * 여는 괄호를 넣을 수 있는지? -> 남아있는 여는 괄호의 개수를 확인한다
 * 닫는 괄호를 넣을 수 있는지? -> 앞에 여는 괄호가 몇 개 들어있는지 확인한다.
 */

import java.util.*

class Solution {
    
    var numOfPair = 0
    val parentheList = mutableListOf<String>()
    val sb = StringBuilder()
    
    fun generateParenthesis(n: Int): List<String> {
        numOfPair = n
        generateParentheses(0, 0)
        
        return parentheList
    }
    
    // 인자: sb의 여닫는 괄호 개수
    fun generateParentheses(openCount: Int, closeCount: Int) {
        // 괄호를 모두 사용 -> 정답에 추가
        if (openCount == numOfPair && closeCount == numOfPair) {
            parentheList.add(sb.toString())
            return
        }
        
        // 여는 괄호가 남아 있다 -> 사용한다
        if (openCount < numOfPair) {
            sb.append('(')
            generateParentheses(openCount + 1, closeCount)
            sb.deleteCharAt(sb.lastIndex)
        }
        
        // 문자열에 여는 괄호가 닫는 괄호보다 많다 -> 닫는 괄호 사용 가능
        if (openCount > closeCount) {
            sb.append(')')
            generateParentheses(openCount, closeCount + 1)
            sb.deleteCharAt(sb.lastIndex)
        }
        
    }
}