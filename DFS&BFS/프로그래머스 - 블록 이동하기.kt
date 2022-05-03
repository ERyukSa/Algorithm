/* https://programmers.co.kr/learn/courses/30/lessons/60063 
   BFS 기반의 구현 문제, 블록 회전을 구현하는 부분이 까다롭다.
   좌표가 2개라서 HashSet을 사용하여 중복 방문을 체크했다.
*/

import java.util.*

class Solution {
    
    data class Robot(val r1: Int, val c1: Int, val r2: Int, val c2: Int, val mCount: Int)
    
    fun solution(board: Array<IntArray>): Int {
        return findMinMoveCount(board)
    }
    
    fun findMinMoveCount(board: Array<IntArray>): Int {
        val n = board.size
        val q: Queue<Robot> = LinkedList()
        val visited = HashSet<String>()
        val dRow = intArrayOf(-1, 1, 0, 0)
        val dCol = intArrayOf(0, 0, -1, 1)
        
        q.add(Robot(0, 0, 0, 1, 0))
        visited.add("0 0 0 1")
    
        while (true) {
            val robot = q.poll()
            
            // 상,하,좌,우 이동
            for (i in 0 until 4) {
                val nR1 = robot.r1 + dRow[i]
                val nC1 = robot.c1 + dCol[i]
                val nR2 = robot.r2 + dRow[i]
                val nC2 = robot.c2 + dCol[i]
                
                if (nR1 !in board.indices || nC1 !in board.indices || nR2 !in board.indices || nC2 !in board.indices) continue
                if (board[nR1][nC1] == 1 || board[nR2][nC2] == 1) continue
                val state = "$nR1 $nC1 $nR2 $nC2"
                if (visited.contains(state)) continue
                if (nR2 == n-1 && nC2 == n-1) {
                    return robot.mCount + 1
                }
                
                visited.add(state)
                q.offer(Robot(nR1, nC1, nR2, nC2, robot.mCount + 1))
            }
            
            // 회전: ㅡ -> ㅣ
            // 양 점을 축으로 위, 아래의 4가지 경우의 수가 나온다
            if (robot.r1 == robot.r2) {
                var nR1 = robot.r2; var nC1 = robot.c2; var nR2 = robot.r1 + 1; var nC2 = robot.c1 + 1
                var state = "$nR1 $nC1 $nR2 $nC2"
                
                // 회전시킨 좌표가 board 안에 있어야 하고, 축의 대각선과 회전시킨 곳에 벽이 있으면 안되고, 처음 확인하는 좌표여야 한다
                if (nR2 in board.indices && board[nR2][robot.c1] == 0 && board[nR2][nC2] == 0 && visited.contains(state).not()){
                    if (nR2 == n-1 && nC2 == n-1) {
                        return robot.mCount + 1
                    }
                    visited.add(state)
                    q.offer(Robot(nR1, nC1, nR2, nC2, robot.mCount + 1))
                }
                
                nR1 = robot.r1; nC1 = robot.c1; nR2 = robot.r2 + 1; nC2 = robot.c2 - 1
                state = "$nR1 $nC1 $nR2 $nC2"
                if (nR2 in board.indices && board[nR2][robot.c2] == 0 && board[nR2][nC2] == 0 && visited.contains(state).not()){
                    if (nR2 == n-1 && nC2 == n-1) {
                        return robot.mCount + 1
                    }
                    visited.add(state)
                    q.offer(Robot(nR1, nC1, nR2, nC2, robot.mCount + 1))
                }
                
                nR1 = robot.r1 - 1; nC1 = robot.c1 + 1; nR2 = robot.r2; nC2 = robot.c2
                state = "$nR1 $nC1 $nR2 $nC2"
                if (nR1 in board.indices && board[nR1][robot.c1] == 0 && board[nR1][nC1] == 0 && visited.contains(state).not()){
                    if (nR2 == n-1 && nC2 == n-1) {
                        return robot.mCount + 1
                    }
                    visited.add(state)
                    q.offer(Robot(nR1, nC1, nR2, nC2, robot.mCount + 1))
                }
                
                nR1 = robot.r2 - 1; nC1 = robot.c2 - 1; nR2 = robot.r1; nC2 = robot.c1
                state = "$nR1 $nC1 $nR2 $nC2"
                if (nR1 in board.indices && board[nR1][robot.c2] == 0 && board[nR1][nC1] == 0 && visited.contains(state).not()){
                    if (nR2 == n-1 && nC2 == n-1) {
                        return robot.mCount + 1
                    }
                    visited.add(state)
                    q.offer(Robot(nR1, nC1, nR2, nC2, robot.mCount + 1))
                }
                
            } else { // 회전: ㅣ -> ㅡ
                var nR1 = robot.r2; var nC1 = robot.c2; var nR2 = robot.r1 + 1; var nC2 = robot.c1 + 1
                var state = "$nR1 $nC1 $nR2 $nC2"
                if (nC2 in board.indices && board[robot.r1][nC2] == 0 && board[nR2][nC2] == 0 && visited.contains(state).not()){
                    if (nR2 == n-1 && nC2 == n-1) {
                        return robot.mCount + 1
                    }
                    visited.add(state)
                    q.offer(Robot(nR1, nC1, nR2, nC2, robot.mCount + 1))
                }
                
                nR1 = robot.r1; nC1 = robot.c1; nR2 = robot.r2 - 1; nC2 = robot.c2 + 1
                state = "$nR1 $nC1 $nR2 $nC2"
                if (nC2 in board.indices && board[robot.r2][nC2] == 0 && board[nR2][nC2] == 0 && visited.contains(state).not()){
                    if (nR2 == n-1 && nC2 == n-1) {
                        return robot.mCount + 1
                    }
                    visited.add(state)
                    q.offer(Robot(nR1, nC1, nR2, nC2, robot.mCount + 1))
                }
                
                nR1 = robot.r1 + 1; nC1 = robot.c1 - 1; nR2 = robot.r2; nC2 = robot.c2
                state = "$nR1 $nC1 $nR2 $nC2"
                if (nC1 in board.indices && board[robot.r1][nC1] == 0 && board[nR1][nC1] == 0 && visited.contains(state).not()){
                    if (nR2 == n-1 && nC2 == n-1) {
                        return robot.mCount + 1
                    }
                    visited.add(state)
                    q.offer(Robot(nR1, nC1, nR2, nC2, robot.mCount + 1))
                }
                
                nR1 = robot.r2 - 1; nC1 = robot.c2 - 1; nR2 = robot.r1; nC2 = robot.c1
                state = "$nR1 $nC1 $nR2 $nC2"
                if (nC1 in board.indices && board[robot.r2][nC1] == 0 && board[nR1][nC1] == 0 && visited.contains(state).not()){
                    if (nR2 == n-1 && nC2 == n-1) {
                        return robot.mCount + 1
                    }
                    visited.add(state)
                    q.offer(Robot(nR1, nC1, nR2, nC2, robot.mCount + 1))
                }
            }
        }
    }
}