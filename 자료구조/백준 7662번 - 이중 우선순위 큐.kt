/**
 * https://www.acmicpc.net/problem/7662
 * Map과 우선순위 큐를 활용해서 풀었다. 방법이 곧바로 떠오르진 않았다.
 * TreeMap을 사용하는 더 빠른 풀이가 있다고 한다. 공부해봐야겠다 .
 */
import java.util.*

private val numbersCount = HashMap<Int, Int>()
private val minPQ = PriorityQueue<Int>()
private val maxPQ = PriorityQueue<Int>(compareByDescending { it })
private val answer = StringBuilder()

fun main() {
    val t = readln().toInt()

    repeat(t) {
        numbersCount.clear()
        minPQ.clear()
        maxPQ.clear()

        val cmdCount = readln().toInt()
        repeat(cmdCount) {
            val cmd = readln().split(" ")
            val number = cmd[1].toInt()
            when (cmd[0]) {
                "I" -> {
                    numbersCount[number] = numbersCount.getOrDefault(number, 0) + 1
                    minPQ.offer(number)
                    maxPQ.offer(number)
                }
                else -> {
                    val heap = if (number == -1) minPQ else maxPQ
                    // 다른 큐에서 이전에 꺼냈던 숫자인 경우, 다시 꺼내서 확인한다
                    while (heap.isNotEmpty()) {
                        val deletedNumber = heap.poll()
                        if (numbersCount[deletedNumber]!! > 0) {
                            numbersCount[deletedNumber] = numbersCount[deletedNumber]!! - 1
                            break
                        }
                    }
                }
            }
        }

        addResultToAnswer()
        // addResultToAnswer2()
    }

    print(answer.toString())
}

// 최소,최대 값을 계산하는 방법1
private fun addResultToAnswer() {
    var doesNumberExist = false
    while (minPQ.isNotEmpty()) {
        if (numbersCount[minPQ.peek()]!! > 0) {
            doesNumberExist = true
            break
        }
        minPQ.poll()
    }
    if (doesNumberExist) {
        while (maxPQ.isNotEmpty()) {
            if (numbersCount[maxPQ.peek()] != 0) break
            maxPQ.poll()
        }
    }

    if (doesNumberExist) {
        answer.append("${maxPQ.peek()} ${minPQ.peek()}\n")
    } else {
        answer.append("EMPTY\n")
    }
}

// 최소,최대 값을 계산하는 방법2
private fun addResultToAnswer2() {
    numbersCount.keys.asSequence().filter { numbersCount[it]!! > 0 }.sorted().toList().let { numbers ->
        if (numbers.isEmpty()) {
            answer.append("EMPTY\n")
        } else {
            answer.append("${numbers.last()} ${numbers.first()}\n")
        }
    }
}