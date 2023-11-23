package `프로그래머스 - 코딩 테스트 공부`

class Solution {

    lateinit var minTimeCache: Array<IntArray> // [alp][cop]
    lateinit var problemList: Array<IntArray>
    var maxReqAlp = 0
    var maxReqCop = 0

    // problems: [alp_req, cop_req, alp_rwd, cop_rwd, cost]
    fun solution(alp: Int, cop: Int, _problems: Array<IntArray>): Int {
        _problems.forEach {
            maxReqAlp = maxOf(maxReqAlp, it[0])
            maxReqCop = maxOf(maxReqCop, it[1])
        }
        problemList = _problems
        minTimeCache = Array(maxReqAlp + 1) { IntArray(maxReqCop + 1) { -1 } }
        minTimeCache[maxReqAlp][maxReqCop] = 0

        // 초기 능력치가 필요한 최대 능력치보다 크게 나올 수도 있다!!!!
        return calculateMinTime(alp.coerceAtMost(maxReqAlp), cop.coerceAtMost(maxReqCop))
    }

    fun calculateMinTime(alp: Int, cop: Int): Int {
        if (minTimeCache[alp][cop] != -1) {
            return minTimeCache[alp][cop]
        }

        var minTime = Int.MAX_VALUE
        if (alp < maxReqAlp) minTime = calculateMinTime(alp + 1, cop) + 1
        if (cop < maxReqCop) minTime = minOf(minTime, calculateMinTime(alp, cop + 1) + 1)

        for ((reqAlp, reqCop, alpReward, copReward, costTime) in problemList) {
            if (alp < reqAlp || cop < reqCop) continue
            // 아래 두 케이스는 무한루프 가능성을 예방한다
            if ((alpReward == 0 && cop >= maxReqCop) || (copReward == 0 && alp >= maxReqAlp)) continue
            if (alpReward == 0 && copReward == 0) continue

            minTime = minOf(
                minTime,
                calculateMinTime(
                    (alp + alpReward).coerceAtMost(maxReqAlp),
                    (cop + copReward).coerceAtMost(maxReqCop)
                ) + costTime
            )
        }

        minTimeCache[alp][cop] = minTime
        return minTime
    }
}