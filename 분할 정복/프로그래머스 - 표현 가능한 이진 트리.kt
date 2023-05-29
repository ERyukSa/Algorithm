import java.util.LinkedList
import kotlin.math.ceil
import kotlin.math.log2
import kotlin.math.pow

/**
 * 프로그래머스 표현 가능한 이진트리
 * https://school.programmers.co.kr/learn/courses/30/lessons/150367#
 * 1. 자신이 유효한 완전이진트리인지 검사한다. => 유요하지 않으면 return false
 * 2. 양쪽 서브 트리도 유효한지 검사한다. => 모두 유효하면 최종적으로 return true
 * 유효 조건: 더미노드의 직계 자식 노드는 더미 노드이어야만 한다.
 *           즉, 더미노드의 자식 중 실제 노드가 있다면 유효하지 않다.
 */

/**
 * 최적화 버전
 */
class Solution {
    fun solution(numbers: LongArray): IntArray {
        return numbers
            .map(this::convertToBinary)
            .map { if(isValidTree(it, 0, it.lastIndex)) 1 else 0 }
            .toList().toIntArray()
    }

    private fun isValidTree(binaryCode: CharArray, start: Int, end: Int): Boolean {
        if (start == end) {
            return true
        }

        val mid = (start + end) / 2
        if (binaryCode[mid] == '0') {
            if (binaryCode[(start + mid) / 2] == '1' || binaryCode[(mid + end) / 2 + 1] == '1') {
                return false
            }
        }

        return isValidTree(binaryCode, start, mid - 1) && isValidTree(binaryCode, mid + 1, end)
    }

    private fun convertToBinary(num: Long): CharArray {
        val linkedList = LinkedList<Long>()
        var number = num
        while (number > 0) {
            linkedList.addFirst(number % 2)
            number /= 2
        }
        makeFullTree(linkedList)
        return linkedList.joinToString("").toCharArray()
    }

    private fun makeFullTree(tree: LinkedList<Long>) {
        val k = ceil(log2(tree.size + 1f)).toInt()
        val minFullTreeSize = (2.toFloat().pow(k) - 1).toInt()
        for (i in 0 until minFullTreeSize - tree.size) {
            tree.addFirst(0)
        }
    }
}

/**
 * 초기 버전
 */
class Solution2 {
    fun solution(numbers: LongArray): IntArray {
        return numbers
            .map(this::convertToBinary)
            .map { if(isValidTree(it, 0, it.lastIndex)) 1 else 0 }
            .toList().toIntArray()
    }

    private fun isValidTree(binaryCode: CharArray, start: Int, end: Int): Boolean {
        if (start == end) {
            return true
        }

        val mid = (start + end) / 2
        if (isValidTree(binaryCode, start, mid - 1).not() || isValidTree(binaryCode, mid + 1, end).not()) {
            return false
        }

        if (binaryCode[mid] == '0') {
            if (binaryCode[(start + mid) / 2] == '1' || binaryCode[(mid + end) / 2 + 1] == '1') {
                return false
            }
        }
        return true
    }

    private fun convertToBinary(num: Long): CharArray {
        val linkedList = LinkedList<Long>()
        var number = num
        while (number > 0) {
            linkedList.addFirst(number % 2)
            number /= 2
        }
        makeFullTree(linkedList)
        return linkedList.joinToString("").toCharArray()
    }

    private fun makeFullTree(tree: LinkedList<Long>) {
        val k = ceil(log2(tree.size + 1f)).toInt()
        val minFullTreeSize = (2.toFloat().pow(k) - 1).toInt()
        for (i in 0 until minFullTreeSize - tree.size) {
            tree.addFirst(0)
        }
    }
}