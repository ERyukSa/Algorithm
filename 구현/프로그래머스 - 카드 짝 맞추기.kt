/** https://programmers.co.kr/learn/courses/30/lessons/72415
 * 삼성 A형 유형의 극한의 구현 문제였다.
 * 순열 + BFS(최단 거리)
 * 움직임이 추가된 최단 거리를 찾는 문제라는 아이디어를 떠올리는 것과 
 * ctrl + move를 구현하는 것이 어려웠다.
 */

import java.util.*
import kotlin.math.min

data class Pos(val row: Int, val col: Int)

class Solution {
    lateinit var mBoard: Array<IntArray>
    val cardLocation = Array(7){ mutableListOf<IntArray>() }
    lateinit var removeCards: Array<IntArray>
    lateinit var order: IntArray
    lateinit var selected: BooleanArray

    val dRow = intArrayOf(-1, 1, 0, 0)
    val dCol = intArrayOf(0, 0, -1, 1)
    var startRow = 0; var startCol = 0

    var minCount = Int.MAX_VALUE

    fun solution(board: Array<IntArray>, r: Int, c: Int): Int {
        mBoard = board
        startRow = r; startCol = c
        val cardSet: MutableSet<Int> = HashSet()

        for (row in 0 until 4) {
            for (col in 0 until 4) {
                if (board[row][col] != 0) {
                    val cardNum = board[row][col]
                    cardSet.add(cardNum)
                    cardLocation[cardNum].add(intArrayOf(row, col))
                }
            }
        }

        removeCards = Array(cardSet.size * 2){ IntArray(2) }
        order = IntArray(cardSet.size)
        selected = BooleanArray(cardSet.size + 1)

        selectOrder(0)
        
        return minCount
    }

    private fun selectOrder(idx: Int) {
        if (idx >= order.size) {
            selectRemoveCards(0)
            return
        }

        for (cardNum in 1..order.size) {
            if (selected[cardNum]) continue
            selected[cardNum] = true
            order[idx] = cardNum
            selectOrder(idx + 1)
            selected[cardNum] = false
        }
    }

    private fun selectRemoveCards(currentOrder: Int) {
        if (currentOrder >= order.size) {
            simulate()
            return
        }

        val cardNum = order[currentOrder]

        removeCards[2 * currentOrder] = cardLocation[cardNum][0]
        removeCards[2 * currentOrder + 1] = cardLocation[cardNum][1]
        selectRemoveCards(currentOrder + 1)

        removeCards[2 * currentOrder] = cardLocation[cardNum][1]
        removeCards[2 * currentOrder + 1] = cardLocation[cardNum][0]
        selectRemoveCards(currentOrder + 1)
    }

    private fun simulate() {
        var count = removeCards.size + getMinVisit(intArrayOf(startRow, startCol), removeCards[0])

        for (i in 0 until removeCards.lastIndex) {
            mBoard[removeCards[i][0]][removeCards[i][1]] = 0
            count += getMinVisit(removeCards[i], removeCards[i+1])
        }

        minCount = min(minCount, count)

        for (i in removeCards.indices) {
            mBoard[removeCards[i][0]][removeCards[i][1]] = order[i / 2]
        }
    }

    private fun getMinVisit(src: IntArray, dst: IntArray): Int {
        if (src[0] == dst[0] && src[1] == dst[1]) return 0

        val visitCount = Array(4) {IntArray(4){-1} }
        visitCount[src[0]][src[1]] = 0
        val q: Queue<Pos> = LinkedList()
        q.add(Pos(src[0], src[1]))

        while (true) {
            val pos = q.poll()

            for (i in 0 until 4) {
                val nextRow = pos.row + dRow[i]
                val nextCol = pos.col + dCol[i]

                if (nextRow < 0 || nextRow >= 4 || nextCol < 0 || nextCol >= 4 || visitCount[nextRow][nextCol]!= -1) {
                    continue
                }

                visitCount[nextRow][nextCol] = visitCount[pos.row][pos.col] + 1
                if (nextRow == dst[0] && nextCol == dst[1]) return visitCount[nextRow][nextCol]
                q.add(Pos(nextRow, nextCol))
            }

            for (i in 0 until 4) {
                val (nextRow, nextCol) = controlMove(pos.row, pos.col, i) ?: continue
                if (visitCount[nextRow][nextCol] != -1) continue

                visitCount[nextRow][nextCol] = visitCount[pos.row][pos.col] + 1
                if (nextRow == dst[0] && nextCol == dst[1]) return visitCount[nextRow][nextCol]
                q.add(Pos(nextRow, nextCol))

            }
        }
    }

    // 0,1,2,3 - 위,아래,왼쪽,오른쪽
    private fun controlMove(row: Int, col: Int, dir: Int): Pair<Int, Int>? {
        var nextRow = row + dRow[dir]
        var nextCol = col + dCol[dir]

        if (nextRow !in mBoard.indices || nextCol !in mBoard.indices) {
            return null
        }

        while (true) {
            if (nextRow !in mBoard.indices || nextCol !in mBoard.indices) {
                return Pair(nextRow - dRow[dir], nextCol - dCol[dir])
            } else if (mBoard[nextRow][nextCol] != 0) {
                return Pair(nextRow, nextCol)
            } 

            nextRow += dRow[dir]
            nextCol += dCol[dir]
        }
    }
}