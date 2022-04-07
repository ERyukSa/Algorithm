/* https://programmers.co.kr/learn/courses/30/lessons/92344 */
/* 이차원 누적합 */

class Solution {
    fun solution(board: Array<IntArray>, skills: Array<IntArray>): Int {
        // 변화량 기록 배열
        val changeBoard = Array(board.size + 1) { IntArray(board[0].size + 1) }

        setSkills(changeBoard, skills)
        updateBoard(board, changeBoard)
        return findBrokenCount(board)
    }

    private fun setSkills(changeBoard: Array<IntArray>, skills: Array<IntArray>) {
        for (skill in skills) {
            val r1 = skill[1]; val c1 = skill[2]
            val r2 = skill[3]; val c2 = skill[4]
            val degree = if (skill[0] == 1) -skill[5] else skill[5]

            changeBoard[r1][c1] += degree; changeBoard[r1][c2 + 1] += -degree
            changeBoard[r2 + 1][c1] += -degree; changeBoard[r2 + 1][c2 + 1] += degree
        }

        // 변화량 일괄 적용
        for(row in 0 until changeBoard.size - 1) {
            for (col in 0 until changeBoard[0].size - 1) {
                changeBoard[row][col + 1] += changeBoard[row][col]
            }
        }

        for(col in 0 until changeBoard[0].size - 1) {
            for (row in 0 until changeBoard.size - 1) {
                changeBoard[row + 1][col] += changeBoard[row][col]
            }
        }
    }

    /**
     * board에 변화량 적용
     */
    private fun updateBoard(board: Array<IntArray>, changeBoard: Array<IntArray>) {
        for (row in board.indices) {
            for (col in board[0].indices) {
                board[row][col] += changeBoard[row][col]
            }
        }
    }

    private fun findBrokenCount(board: Array<IntArray>): Int {
        var count = 0

        for (row in board.indices) {
            for (col in board[0].indices) {
                if (board[row][col] > 0) count++
            }
        }

        return count
    }
}