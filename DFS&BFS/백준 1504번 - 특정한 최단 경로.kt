/**
 * https://www.acmicpc.net/problem/1504
 * 다익스트라 응용
 */
import java.util.*

data class Edge(val toNode: Int, val distance: Int)
data class State(val node: Int, val accDistance: Int)

private lateinit var graph: Array<MutableList<Edge>>
private lateinit var minDistFromSrc: IntArray

fun main() {
    val (nodeCount, edgeCount) = readln().split(" ").map(String::toInt)
    graph = Array(nodeCount + 1) { mutableListOf() }
    repeat(edgeCount) {
        val (node1, node2, distance) = readln().split(" ").map(String::toInt)
        graph[node1].add(Edge(toNode = node2, distance = distance))
        graph[node2].add(Edge(toNode = node1, distance = distance))
    }
    val (midNode1, midNode2) = readln().split(" ").map(String::toInt)

    minDistFromSrc = dijkstra(1)
    val minDistFromMid1 = dijkstra(midNode1)
    val minDistFromMid2 = dijkstra(midNode2)
    val distance1 = calculateTotalDistance(midNode1, minDistFromMid1, midNode2, minDistFromMid2)
    val distance2 = calculateTotalDistance(midNode2, minDistFromMid2, midNode1, minDistFromMid1)

    if (distance1 == -1 && distance2 == -1) {
        print(-1)
    } else if (distance1 == -1) {
        print(distance2)
    } else if (distance2 == -1) {
        print(distance1)
    } else {
        print(minOf(distance1, distance2))
    }
}

fun dijkstra(startNode: Int): IntArray {
    val q = PriorityQueue<State>(compareBy { it.accDistance })
    val minDistFromStart = IntArray(graph.size) { Int.MAX_VALUE }

    q.offer(State(node = startNode, accDistance = 0))
    minDistFromStart[startNode] = 0
    while (q.isNotEmpty()) {
        val (node, accDistance) = q.poll()
        if (accDistance > minDistFromStart[node]) {
            continue
        }

        for (nextEdge in graph[node]) {
            val addedDistance = accDistance + nextEdge.distance
            if (addedDistance < minDistFromStart[nextEdge.toNode]) {
                q.offer(State(node = nextEdge.toNode, accDistance = addedDistance))
                minDistFromStart[nextEdge.toNode] = addedDistance
            }
        }
    }

    return minDistFromStart
}

private fun calculateTotalDistance(
    firstMidNode: Int,
    minDistFromFirstMid: IntArray,
    secondMidNode: Int,
    minDistFromSecondMid: IntArray
): Int {
    val destinationNode = minDistFromFirstMid.lastIndex

    return if (minDistFromSrc[firstMidNode] == Int.MAX_VALUE ||
        minDistFromFirstMid[secondMidNode] == Int.MAX_VALUE ||
        minDistFromSecondMid[destinationNode] == Int.MAX_VALUE
    ) {
        -1
    } else {
        minDistFromSrc[firstMidNode] + minDistFromFirstMid[secondMidNode] + minDistFromSecondMid[destinationNode]
    }
}