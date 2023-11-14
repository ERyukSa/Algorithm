/**
 * 단기간 성장
 * https://www.acmicpc.net/workbook/view/4349
 */

/**
 * https://www.acmicpc.net/problem/9370 미확인 도착지
 * 유형: 다익스트라
 */

import java.util.LinkedList
import java.util.StringTokenizer

data class Edge(val to: Int, val distance: Int)
data class Route(val current: Int, val totalDistance: Int)

fun main() {
    repeat(readln().toInt()) {
        val (nodeCount, edgeCount, endListCount) = readln().split(" ").map(String::toInt)
        val (start, mid1, mid2) = readln().split(" ").map(String::toInt)
        val graph = Array(nodeCount + 1) { mutableListOf<Edge>() }

        repeat(edgeCount) {
            val st = StringTokenizer(readln())
            val node1 = st.nextToken().toInt()
            val node2 = st.nextToken().toInt()
            val distance = st.nextToken().toInt()
            graph[node1].add(Edge(node2, distance))
            graph[node2].add(Edge(node1, distance))
        }

        val distanceFromStart = getDistancesFrom(start, graph)
        val distanceFromMid1 = getDistancesFrom(mid1, graph)
        val distanceFromMid2 = getDistancesFrom(mid2, graph)
        val middleDistance = distanceFromMid1[mid2]
        val possibleEndList = mutableListOf<Int>()

        repeat(endListCount) {
            val end = readln().toInt()
            val distanceToEnd = minOf(
                distanceFromStart[mid1] + middleDistance + distanceFromMid2[end],
                distanceFromStart[mid2] + middleDistance + distanceFromMid1[end]
            )
            if (distanceToEnd == distanceFromStart[end]) {
                possibleEndList.add(end)
            }
        }

        possibleEndList.run {
            sort()
            println(joinToString(" "))
        }
    }
}

// 다익스트라
fun getDistancesFrom(start: Int, graph: Array<MutableList<Edge>>): IntArray {
    val queue = LinkedList<Route>()
    val distanceFromStart = IntArray(graph.size) { Int.MAX_VALUE }

    queue.offer(Route(start, 0))
    distanceFromStart[start] = 0

    while(queue.isNotEmpty()) {
        val (currentNode, totalDistance) = queue.poll()
        if (totalDistance > distanceFromStart[currentNode]) {
            continue
        }

        for ((nextNode, nextDistance) in graph[currentNode]) {
            val nextTotalDistance = totalDistance + nextDistance
            if (nextTotalDistance < distanceFromStart[nextNode]) {
                distanceFromStart[nextNode] = nextTotalDistance
                queue.offer(Route(nextNode, nextTotalDistance))
            }
        }
    }

    return distanceFromStart
}