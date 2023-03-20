/**
 * https://www.acmicpc.net/problem/1655
 * 우선순위 큐, 어렵다
 */
import java.util.PriorityQueue

fun main() {
    val n = readln().toInt()
    val leftMaxHeap = PriorityQueue<Int>(compareByDescending { it })
    val rightMinHeap = PriorityQueue<Int>()
    val sb = StringBuilder(n)

    readln().toInt().also { firstNumber ->
        leftMaxHeap.offer(firstNumber)
        sb.append(firstNumber).appendLine()
    }

    repeat(n - 1) {
        val number = readln().toInt()
        if (leftMaxHeap.size > rightMinHeap.size) {
            rightMinHeap.offer(number)
        } else {
            leftMaxHeap.offer(number)
        }

        if (leftMaxHeap.peek() > rightMinHeap.peek()) {
            rightMinHeap.offer(leftMaxHeap.poll())
            leftMaxHeap.offer(rightMinHeap.poll())
        }

        sb.append(leftMaxHeap.peek()).appendLine()
    }

    print(sb.toString())
}
