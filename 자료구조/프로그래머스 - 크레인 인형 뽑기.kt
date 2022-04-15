/* https://programmers.co.kr/learn/courses/30/lessons/64061 */
/* Queue, Stack 사용 */

import java.util.*

class Solution {
    fun solution(board: Array<IntArray>, moves: IntArray): Int {
        val boardQueue: Array<Queue<Int>> = Array(board.size){ LinkedList() }
        
        for (row in board.indices) {
            for (col in board.indices) {
                if (board[row][col] != 0) {
                    boardQueue[col].offer(board[row][col])
                }
            }
        }
        
        return getRemovedCount(boardQueue, moves)
    }
    
    fun getRemovedCount(boardQueue: Array<Queue<Int>>, moves: IntArray): Int {
        var answer = 0
        val basket = LinkedList<Int>()
        
        for (m in moves) {
            val queue = boardQueue[m - 1]
            
            if (queue.isNotEmpty()) {
                val doll = queue.poll()
                
                if (basket.isNotEmpty() && basket.peekLast() == doll) {
                    answer++
                    basket.removeLast()
                } else {
                    basket.add(doll)
                }
            } 
        }
        
        return answer * 2
    }
}