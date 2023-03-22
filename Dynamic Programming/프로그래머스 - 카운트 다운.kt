/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/131129
 * 완전 탐색(DFS) + DP, 어렵다
 */

data class Result(val dartCount: Int, val singleBoolCount: Int) {
    operator fun plus(other: Result): Result =
        Result(this.dartCount + other.dartCount, this.singleBoolCount + other.singleBoolCount)
}

class Solution {
    private lateinit var bestResult: Array<Result?>
    private val initialResult = Result(0, 0)
    private val singleBoolStartResult = Result(1, 1)
    private val nonSingleBoolStartResult = Result(1, 0)

    fun solution(target: Int): IntArray {
        bestResult = Array(target + 1) { null }
        val answerResult= findBestResult(target)
        return intArrayOf(answerResult.dartCount, answerResult.singleBoolCount)
    }

    private fun findBestResult(targetScore: Int): Result {
        if (bestResult[targetScore] != null) {
            return bestResult[targetScore]!!
        }

        if (targetScore == 0) {
            return Result(0, 0)
        }

        var bestResultAfterNow = initialResult

        for (score in 1..20) {
            for (bonus in 1..3) {
                val hitScore = score * bonus
                if (targetScore < hitScore) continue
                val startResult = if (bonus == 1) singleBoolStartResult else nonSingleBoolStartResult
                val bestResultCandidate = startResult + findBestResult(targetScore - hitScore)
                bestResultAfterNow = getBetterResult(bestResultAfterNow, bestResultCandidate)
            }
        }

        if (targetScore >= 50) {
            val bestResultCandidate = singleBoolStartResult + findBestResult(targetScore - 50)
            bestResultAfterNow = getBetterResult(bestResultAfterNow, bestResultCandidate)
        }

        bestResult[targetScore] = bestResultAfterNow
        return bestResultAfterNow
    }

    private fun getBetterResult(oldResult: Result, newResult: Result): Result {
        return if (oldResult === initialResult) {
            newResult
        } else if (oldResult.dartCount < newResult.dartCount) {
            oldResult
        } else if (oldResult.dartCount == newResult.dartCount && oldResult.singleBoolCount > newResult.singleBoolCount) {
            oldResult
        } else {
            newResult
        }
    }
}