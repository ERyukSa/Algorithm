/**
 * https://www.acmicpc.net/problem/1914
 * 재귀, DP, 큰 수 계산 사용
 * DP로 풀었다. 다른 사람들의 코드를 살펴보니 원판 움직이는 개수 공식이 따로 있는 것 같다.
 */
import java.lang.StringBuilder
import java.math.BigInteger

private var circleCount = 0
private var moveCount = 0
private val moveProcess = StringBuilder()

// 개수만 세는 로직용 메모이제이션 테이블
// moveCountTable[i]: 원판 개수 i를 다른 장대로 옮길 때 원판이 이동하는 횟수
private lateinit var moveCountTable: Array<BigInteger>

fun main() {
    circleCount = readln().toInt()
    // 원판이 20개 이하일 경우 재귀적으로 개수를 세면서 직접 원판을 옮긴다
    if (circleCount <= 20) {
        moveCircles(1, 2, 3, circleCount)
        println(moveCount)
        if (circleCount <= 20) {
            print(moveProcess.toString())
        }
    } else {
        // 20개 초과일 경우 원판 옮기는 개수만 세는 로직 실행
        moveCountTable = Array(circleCount + 1) { BigInteger.ZERO }
        moveCountTable[1] = BigInteger.ONE
        countCircleMovement(circleCount).let { print(it) }
    }
}

private fun moveCircles(fromPoll: Int, midPoll: Int, toPoll: Int, circleCount: Int) {
    if (circleCount == 1) {
        moveTopCircle(fromPoll, toPoll)
        return
    }

    moveCircles(fromPoll = fromPoll, midPoll = toPoll, toPoll = midPoll, circleCount - 1)
    moveTopCircle(fromPoll, toPoll)
    moveCircles(fromPoll = midPoll, midPoll = fromPoll, toPoll = toPoll, circleCount - 1)
}

private fun moveTopCircle(fromPoll: Int, toPoll: Int) {
    moveCount++
    if (circleCount <= 20) {
        moveProcess.append("$fromPoll $toPoll\n")
    }
}

private fun countCircleMovement(circleCount: Int): BigInteger {
    // base 사례인 circleCount == 1인 경우까지 포함된다
    if (moveCountTable[circleCount] != BigInteger.ZERO) {
        return moveCountTable[circleCount]
    }

    moveCountTable[circleCount] = countCircleMovement(circleCount - 1) + BigInteger.ONE + countCircleMovement(circleCount - 1)
    return moveCountTable[circleCount]
}