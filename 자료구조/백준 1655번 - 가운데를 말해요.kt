/**
 * https://www.acmicpc.net/problem/1655
 * 우선순위 큐, 어렵다
 */
import java.util.PriorityQueue

fun mainTry1() {
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

/**
 * 단기간 성장
 * https://www.acmicpc.net/workbook/view/4349
 */

/**
 * https://www.acmicpc.net/problem/1655 가운데를 말해요
 * 유형: 정렬, 우선순위 큐, 풀이 시간: 35분
 * 예전에 풀어봤던 문제다. 당시에 해결 방식이 인상 깊었어서 우선순위 큐 두 개를 사용하는 아이디어가 금방 떠올랐다.
 * 그럼에도 불구하고 어떤 우선순위 큐에 먼저 넣고, 언제 교체해야 하는지 알고리즘을 구체화하는데 시간이 걸렸다.
 * 노트에 필기를 할수록 문제 푸는 시간이 단축된다. 분기가 조금이라도 복잡해지기 시작하면 머리로만 풀기엔 한계가 온다.
 */

/*
import java.util.PriorityQueue
*/

fun mainTry2() {
    val leftMaxPQ = PriorityQueue<Int>(compareByDescending { it })
    val rightMinPQ = PriorityQueue<Int>()
    val numberCount = readln().toInt()
    val stringBuilder = StringBuilder()

    leftMaxPQ.offer(readln().toInt())
    stringBuilder.append(leftMaxPQ.peek()).appendLine()

    repeat(numberCount - 1) { i ->
        rightMinPQ.offer(readln().toInt())
        if (leftMaxPQ.size == rightMinPQ.size) {
            if (leftMaxPQ.peek() > rightMinPQ.peek()) {
                rightMinPQ.offer(leftMaxPQ.poll())
                leftMaxPQ.offer(rightMinPQ.poll())
            }
        } else {
            leftMaxPQ.offer(rightMinPQ.poll())
        }
        stringBuilder.append(leftMaxPQ.peek()).appendLine()
    }

    print(stringBuilder.toString())
}