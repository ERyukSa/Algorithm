package `백준 11657번 - 타임머신`

/**
 * 단기간 성장
 * https://www.acmicpc.net/workbook/view/4349
 */

/**
* https://www.acmicpc.net/problem/11657 타임머신
* 유형: 벨만 포드
*/

data class Edge(val startCity: Int, val endCity: Int, val takenTime: Int)

fun main() {
    val (cityCount, edgeCount) = readln().split(" ").map(String::toInt)
    val edgeList = mutableListOf<Edge>()

    repeat(edgeCount) {
        val (fromCity, toCity, takenTime) = readln().split(" ").map(String::toInt)
        edgeList.add(Edge(fromCity, toCity, takenTime))
    }

    // 벨만-포드 알고리즘
    val takenTimeTable = LongArray(cityCount + 1) { Long.MAX_VALUE }
    takenTimeTable[1] = 0

    repeat(cityCount) { i ->
        for (edge in edgeList) {
            if (takenTimeTable[edge.startCity] == Long.MAX_VALUE) continue

            val newTakenTimeToEndCity =  takenTimeTable[edge.startCity] + edge.takenTime
            if (takenTimeTable[edge.endCity] > newTakenTimeToEndCity) {
                takenTimeTable[edge.endCity] = newTakenTimeToEndCity

                // cityCount번째 반복에서 시간이 갱신되면 음의 순환을 갖고 있다는 뜻이다
                if (i == cityCount - 1) {
                    print("-1")
                    return
                }
            }
        }
    }

    val sb = StringBuilder()
    for (city in 2..cityCount) {
        if (takenTimeTable[city] == Long.MAX_VALUE) { // 해당 도시로 가는 경로가 없다
            sb.append("-1\n")
        } else {
            sb.append("${takenTimeTable[city]}\n")
        }
    }
    print(sb.toString())
}