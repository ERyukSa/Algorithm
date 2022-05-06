/* https://programmers.co.kr/learn/courses/30/lessons/92345 */
/**
 * 누가 이길지 이미 정해진 것처럼 나오지만 실제로 모든 경우의 수를 확인하기 전까진
 * 우리는 누가 이길지 알 수 없다. 따라서, 완전 탐색으로 전부 확인한다.
 * 내가 어딘가로 움직인 후 다음 호출에서는 상대가 움직이도록 재귀적으로 함수를 구성한다.
 * "결과"로는 내가 각 방향으로 움직였을 때 "(누가 이기는지, A와 B가 각각 몇 번 움직였는지)"를 반환한다.
 * 내가 이기는 경우가 하나라도 있으면 그것을 선택하도록 한다.
 * 그것이 실수하지 않으면 항상 이기도록 플레이 하는 방법이다. 
 * ex) 1,2,3,4 중 1로 가면 이기고, 2,3,4로 가면 진다 -> 1로 가면(실수하지 않으면) 항상 이긴다.
 * 따라서, A가 먼저 움직이기 때문에 A의 첫 재귀 호출 결과중 A가 이기는 경우가 하나라도 이긴다면 A가 이기고,
 * 모두 지는 경우에만 B가 항상 이기게 된다. (이제 보니 A에게 유리한 게임이었다.)
 * 서로 이기기 위해 최적의 플레이 해야 한다. 따라서 결과 값이 모두 내가 진다면,
 * 그 중 나와 상대방이 모두 가장 많이 움직였고, 그 중에서도 내가 제일 많이 움직인 경우를 반환한다.
 */
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
     * 매번 해당하는 턴의 플레이어를 움직인다. 현재 A를 움직였으면, 다음은 B를 움직이도록 재귀 호출한다.
     * 다음에 움직일 플레이어를 모든 방향으로 움직여본다.
     * 그 결과 중 자신이 이기는 게 있으면 그 중 자신이 최소로 움직인 경우를 반환,
     * 모두 진다면 A,B 모두가 가장 많이 움직인 경우를을 반환한다. 
     * --> 지지 않기 위해 최선의 노력을 한 경우이기 때문이다.
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
        
        // 결과가 없다 -> 움직일 수 없었다 -> 나의 패배
        if (resultList.isEmpty()) {
            return Triple(nextTurn, aMove, bMove)
        }
        
        // Triple(이긴 사람, a이동횟수, b횟수)
        // 현재 턴의 플레이어를 기준으로 생각한다
        return if (turn == 0) {
            // {내가 이기는 경우를 우선으로 정렬}, {내가 가장 적게 움직인 순으로 정렬}, {상대가 적게 움직인 순으로 정렬}
            resultList.sortWith(compareBy({it.first}, {it.second}, {it.third}))
            if (resultList[0].first == 0) { // 내가(A - 0) 이기는 경우가 있으면
                resultList.first()          // 가장 적게 움직인 것을 출력
            } else {
                // 내가 이긴 게 없으면, 마지막 값이 A,B 모두가 가장 많이 움직 경우다
                // -> 최대한 오래 버텼다는 의미 -> 최대한 지지 않으려고 최선의 플레이 했다.
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