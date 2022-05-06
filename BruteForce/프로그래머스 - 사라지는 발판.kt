/* https://programmers.co.kr/learn/courses/30/lessons/92345 */
class Solution {

    lateinit var mBoard: Array<IntArray>
    val dRow = intArrayOf(-1, 1, 0, 0)
    val dCol = intArrayOf(0, 0, -1, 1)
    
    fun solution(board: Array<IntArray>, aloc: IntArray, bloc: IntArray): Int {
        mBoard = board
        val answer = moveAndGetResult(0, aloc[0], aloc[1], bloc[0], bloc[1], 0, 0)
        return answer.second + answer.third
    }
    
    /**
     * 재귀 함수
     * @param1: turn - 누구 차례인지(0 - A, 1 - B), aMove: a가 움직인 횟수, bMove: b가 움직인 횟수
     * @return: Triple(이긴 사람(0 or 1), aMove, bMove)
     * 매번 해당하는 턴의 플레이어를 움직인다. 이전에 A를 움직였으면, 다음은 B를 움직이도록 재귀 호출한다.
     * 다음에 움직일 플레이어를 모든 방향으로 움직여본다.
     * 그 결과 중 자신이 이기는 게 있으면 그 중 자신이 최소로 움직인 경우를 반환,
     * 모두 진다면 자신이 가장 많이 움직인 값을 반환한다. 
     * --> 그것이 이기기 위해 최선의 노력을 하는 방향이기 때문이다.
     */
    fun moveAndGetResult(turn: Int, aRow: Int, aCol: Int, bRow: Int, bCol: Int, aMove: Int, bMove: Int): Triple<Int, Int, Int> {
        // 둘이 같은 곳에 있었다가 내 발판이 사라진 경우 -> 해당 턴의 플레이어 패배
        if (turn == 0 && mBoard[aRow][aCol] == 0) {
            return Triple(1, aMove, bMove)
        } else if (turn == 1 && mBoard[bRow][bCol] == 0) {
            return Triple(0, aMove, bMove)
        }
        
        val resultList = mutableListOf<Triple<Int, Int, Int>>()
        val nextTurn = (turn + 1) % 2
        
        if (turn == 0) { // A차례
            mBoard[aRow][aCol] = 0
            
            for (i in 0 until 4) {
                val nextRow = aRow + dRow[i]
                val nextCol = aCol + dCol[i]
                // 벗어나거나 발판이 없는 경우
                if (nextRow !in mBoard.indices || nextCol !in mBoard[0].indices) continue
                if (mBoard[nextRow][nextCol] == 0) continue
                
                val result = moveAndGetResult(nextTurn, nextRow, nextCol, bRow, bCol, aMove + 1, bMove)
                resultList.add(result)
            }
            
            mBoard[aRow][aCol] = 1
        } else { // B차례
            mBoard[bRow][bCol] = 0
            
            for (i in 0 until 4) {
                val nextRow = bRow + dRow[i]
                val nextCol = bCol + dCol[i]
                if (nextRow !in mBoard.indices || nextCol !in mBoard[0].indices) continue
                if (mBoard[nextRow][nextCol] == 0) continue
                
                val result = moveAndGetResult(nextTurn, aRow, aCol, nextRow, nextCol, aMove, bMove + 1)
                resultList.add(result)
            }
            
            mBoard[bRow][bCol] = 1
        }
        
        // 결과가 없다 -> 움직일 수가 없었다 -> 다음 턴의 상대 승리
        if (resultList.isEmpty()) {
            return Triple(nextTurn, aMove, bMove)
        }
        
        return if (turn == 0) {
            resultList.sortWith(compareBy({it.first}, {it.second}, {it.third}))
            if (resultList[0].first == 0) {
                resultList.first()
            } else {
                resultList.last()
            }
        } else {
            resultList.sortWith(compareBy({-it.first}, {it.third}, {it.second}))
            if (resultList[0].first == 1) {
                resultList.first()
            } else {
                resultList.last()
            }
        }
    }
}