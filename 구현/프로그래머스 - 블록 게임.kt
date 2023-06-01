/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/42894
 * 2019 카카오 블라인드 코테, 유형: 구현, 난이도: 상
 * 지문 해석 능력 ==> 없앨 수 있는 블록 유형 캐치, 구현 능력 ==> 블록을 떨어뜨릴 수 있는지 판단 & 블록을 어떻게 없애것인지?
 * 1. 보드를 순회하며 블록을 발견한다
 * 2. 발견 -> 없앨 수 있는 블록 유형인지 확인한다
 * 3. 없앨 수 있다 -> 원하는 위치로 블록 낱개를 떨어뜨릴 수 있는지 확인한다
 * 4. 떨어뜨릴 수 있다 -> 블록을 지운다
 * 5. 블록을 지울 수 없을 때까지 1~4번을 반복한다
 */

class `블록 게임` {

    private var removeCount = 0
    private lateinit var board: Array<IntArray>

    fun solution(_board: Array<IntArray>): Int {
        board =_board
        findRemovableBlock()
        return removeCount
    }

    private fun findRemovableBlock() {
        for (row in board.indices) {
            for (col in board[0].indices) {
                if (board[row][col] == 0) continue

                val type = board[row][col]

                if (isBlockA(row, col, type)) {
                    if (canDrop(row, col + 1, type) && canDrop(row, col + 2, type)) {
                        remove(row, col, row + 1, col, row + 1, col + 1, row + 1, col + 2)
                        removeCount++
                        findRemovableBlock()
                    }
                } else if (isBlockB(row, col, type)) {
                    if (canDrop(row + 1, col - 1, type)) {
                        remove(row, col, row + 1, col, row + 2, col, row + 2, col - 1)
                        removeCount++
                        findRemovableBlock()
                    }
                } else if (isBlockC(row, col, type)) {
                    if (canDrop(row + 1, col + 1, type)) {
                        remove(row, col, row + 1, col, row + 2, col, row + 2, col + 1)
                        removeCount++
                        findRemovableBlock()
                    }
                } else if (isBlockD(row, col, type)) {
                    if (canDrop(row, col - 2, type) && canDrop(row, col - 1, type)) {
                        remove(row, col, row + 1, col, row + 1, col - 1, row + 1, col - 2)
                        removeCount++
                        findRemovableBlock()
                    }
                } else if (isBlockE(row, col, type)) {
                    if (canDrop(row, col - 1, type) && canDrop(row, col + 1, type)) {
                        remove(row, col, row + 1, col, row + 1, col - 1, row + 1, col + 1)
                        removeCount++
                        findRemovableBlock()
                    }
                }
            }
        }
    }

    private fun isBlockA(row: Int, col: Int, type: Int): Boolean {
        if (row + 1 >= board.size || col + 2 >= board[0].size) {
            return false
        }
        return board[row + 1][col] == type && board[row + 1][col + 1] == type && board[row + 1][col + 2] == type
    }

    private fun isBlockB(row: Int, col: Int, type: Int): Boolean {
        if (row + 2 >= board.size || col - 1 < 0) {
            return false
        }
        return board[row + 1][col] == type && board[row + 2][col] == type && board[row + 2][col - 1] == type
    }
    private fun isBlockC(row: Int, col: Int, type: Int): Boolean {
        if (row + 2 >= board.size || col + 1 >= board[0].size) {
            return false
        }
        return board[row + 1][col] == type && board[row + 2][col] == type && board[row + 2][col + 1] == type
    }
    private fun isBlockD(row: Int, col: Int, type: Int): Boolean {
        if (row + 1 >= board.size || col - 2 < 0) {
            return false
        }
        return board[row + 1][col] == type && board[row + 1][col - 1] == type && board[row + 1][col - 2] == type
    }
    private fun isBlockE(row: Int, col: Int, type: Int): Boolean {
        if (row + 1 >= board.size || col + 1 >= board[0].size || col - 1 < 0) {
            return false
        }
        return board[row + 1][col] == type && board[row + 1][col - 1] == type && board[row + 1][col + 1] == type
    }

    private fun canDrop(row: Int, col: Int, type: Int): Boolean {
        for (i in 0..row) {
            if (board[i][col] != 0) {
                return false
            }
        }
        return true
    }

    private fun remove(row1: Int, col1: Int, row2: Int, col2: Int, row3: Int, col3: Int, row4: Int, col4: Int) {
        board[row1][col1] = 0
        board[row2][col2] = 0
        board[row3][col3] = 0
        board[row4][col4] = 0
    }
}