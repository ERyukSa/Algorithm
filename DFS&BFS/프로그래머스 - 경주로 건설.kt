/* https://programmers.co.kr/learn/courses/30/lessons/67259 */
/* 완탐: BFS or DFS + 메모이제이션 */

import java.util.LinkedList

class Solution {
    data class State(val row: Int, val col: Int, val dir: Int, val cost: Int)
    
    val dRow = intArrayOf(-1, 1, 0 ,0)
    val dCol = intArrayOf(0, 0, -1, 1)
    
    fun solution(board: Array<IntArray>): Int {
        var answer = 0
        // cost[행][열][방향]
        val cost = Array(board.size){ 
            Array(board.size) {
                IntArray(4) { -1 }
            }
        }
        
        // 0: up, 1: down, 2: left, 3: right
        for (i in 0 until 4) {
            cost[0][0][i] = 0
        }
        
        val q: Queue<State> = LinkedList()
        q.offer(State(0, 0, -1, 0))
        
        while(q.isNotEmpty()) {
            val state = q.poll()
            
            for (i in 0 until 4) {
                val nextRow = state.row + dRow[i]
                val nextCol = state.col + dCol[i]
                
                // board를 벗어나거나 벽으로 막혀 있는 경우
                if (nextRow !in board.indices || nextCol !in board.indices || board[nextRow][nextCol] == 1 ) continue
                
                // 이전에 같은 방향으로 방문한적이 있고, 현재 cost 비용이 이전보다 비싸거나 같을 경우
                val nextCost = state.cost + calculateCornerCost(state.dir, i)
                if (cost[nextRow][nextCol][i] != -1 && cost[nextRow][nextCol][i] <= nextCost) continue
                
                cost[nextRow][nextCol][i] = nextCost
                q.offer(State(nextRow, nextCol, i, nextCost))   
            }
        }
        
        return cost[board.lastIndex][board.lastIndex].filterNot{ it == -1 }.minOrNull()!!
    }
    
    // 직선: 100, 코너 돌 때: 600
    fun calculateCornerCost(prevDir: Int, nextDir: Int): Int {
        return if ((prevDir == 0 || prevDir == 1) && (nextDir == 2 || nextDir == 3)) {
            600
        } else if ((prevDir == 2 || prevDir == 3) && (nextDir == 0 || nextDir == 1)) {
            600
        } else {
            100
        }
    }
}


/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/67259
 * 2023-03-28 스터디 실전 문제
 * 완전 탐색 + DP
 * 모든 경우를 탐색하면서 중복을 피하려면 방향 속성을 포함한 3차원 배열에 캐싱해야 함
 */
data class State(val row: Int, val col: Int, val dir: Int, val cost: Int)
// dir) 0:상, 1:하, 2:좌, 3:우

class `Solution경주로 건설` {

    private lateinit var minCost: Array<Array<IntArray>>

    fun solution(board: Array<IntArray>): Int {
        minCost = Array(board.size) {
            Array(board.size) {
                IntArray(4) { Int.MAX_VALUE }
            }
        }

        val dRow = intArrayOf(-1 , 1, 0, 0)
        val dCol = intArrayOf(0, 0, -1, 1)

        val q = LinkedList<State>()
        q.offer(State(0, 0, 1, 0))
        q.offer(State(0, 0, 3, 0))
        for (i in 0 until 4) {
            minCost[0][0][i] = 0
        }

        while (q.isNotEmpty()) {
            val (row, col, dir, cost) = q.poll()
            if (cost > minCost[row][col][dir]) {
                continue
            }
            if (row == board.lastIndex && col == board.lastIndex) {
                continue
            }

            for (nextDir in 0 until 4) {
                val nextRow = row + dRow[nextDir]
                val nextCol = col + dCol[nextDir]
                if (nextRow !in board.indices || nextCol !in board.indices) continue
                if (board[nextRow][nextCol] == 1) continue

                val nextCost = cost + calculateCost(dir, nextDir)

                if (nextCost < minCost[nextRow][nextCol][nextDir]) {
                    q.offer(State(nextRow, nextCol, nextDir, nextCost))
                    minCost[nextRow][nextCol][nextDir] = nextCost
                }
            }
        }

        return minCost[board.lastIndex][board.lastIndex].minOrNull()!!
    }

    private fun calculateCost(prevDir: Int, newDir: Int): Int =
        when (newDir) {
            0, 1 -> if (prevDir == 2 || prevDir == 3) 600 else 100
            else -> if (prevDir == 0 || prevDir == 1) 600 else 100
        }
}