/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/131129
 * 완전 탐색(DFS) + DP, 어렵다
 */

data class Result(val dartCount: Int, val singleBoolCount: Int)

class Solution {

    private lateinit var bestResult: Array<Result>
    private val initialResult = Result(0, 0)

    fun solution(target: Int): IntArray {
        bestResult = Array(target + 1) { initialResult }
        val answerResult= findBestResult(targetScore = target, dartCount = 0, singleBoolCount = 0)
        return intArrayOf(answerResult.dartCount, answerResult.singleBoolCount)
    }

    private fun findBestResult(targetScore: Int, dartCount: Int, singleBoolCount: Int): Result {
        if (targetScore < 0) {
            return initialResult
        }

        if (bestResult[targetScore] != initialResult) {
            return bestResult[targetScore]
        }

        if (targetScore == 0) {
            return Result(dartCount, singleBoolCount)
        }

        var bestResultAfterNow = initialResult

        for (score in 1..20) {
            for (bonus in 1..3) {
                val hitScore = score * bonus
                val nextSingleBoolCount = if (bonus == 1) 1 else 0
                val bestResultCandidate = findBestResult(targetScore - hitScore, 1, nextSingleBoolCount)
                if (bestResultCandidate != initialResult) {
                    bestResultAfterNow = getBetterResult(bestResultAfterNow, bestResultCandidate)
                }
            }
        }

        val boolResult = findBestResult(targetScore - 50, dartCount + 1, singleBoolCount + 1)
        if (boolResult != initialResult) {
            bestResultAfterNow = getBetterResult(bestResultAfterNow, boolResult)
        }

        bestResult[targetScore] = Result(
            dartCount = dartCount + bestResultAfterNow.dartCount,
            singleBoolCount = singleBoolCount + bestResultAfterNow.singleBoolCount
        )
        return bestResult[targetScore]
    }

    private fun getBetterResult(oldResult: Result, newResult: Result): Result {
        if (newResult.dartCount == 2 && newResult.singleBoolCount == 2) {
            println(oldResult)
            println(newResult)
        }
        return if (oldResult == initialResult) {
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