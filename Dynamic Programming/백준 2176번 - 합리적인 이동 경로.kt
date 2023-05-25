/**
 * https://www.acmicpc.net/problem/2176
 * 다익스트라 + DP
 * 어렵다
 */
import java.util.PriorityQueue

data class Edge(val toNode: Int, val distance: Int)
data class State(val node: Int, val accDistance: Int)

private const val END_NODE = 2
private lateinit var graph: Array<MutableList<Edge>>
private lateinit var minDistFromEnd: IntArray
private lateinit var routeCountCache: IntArray

fun main() {
    val (nodeCount, edgeCount) = readln().split(" ").map(String::toInt)
    graph = Array(nodeCount + 1) { mutableListOf() }
    minDistFromEnd = IntArray(nodeCount + 1) { Int.MAX_VALUE }
    routeCountCache = IntArray(nodeCount + 1) { -1 }
    routeCountCache[END_NODE] = 1

    repeat(edgeCount) {
        val (node1, node2, distance) = readln().split(" ").map(String::toInt)
        graph[node1].add(Edge(node2, distance))
        graph[node2].add(Edge(node1, distance))
    }

    dijkstraFromEnd()
    val routeCount = findRouteCountFrom(1)
    print(routeCount)
}

private fun findRouteCountFrom(node: Int): Int {
    if (routeCountCache[node] != -1) {
        return routeCountCache[node]
    }

    var routeCountFromHere = 0
    for (edge in graph[node]) {
        if (minDistFromEnd[edge.toNode] < minDistFromEnd[node]) {
            routeCountFromHere += findRouteCountFrom(edge.toNode)
        }
    }
    routeCountCache[node] = routeCountFromHere
    return routeCountFromHere
}

private fun dijkstraFromEnd() {
    val q = PriorityQueue<State>(compareBy { it.accDistance })
    q.offer(State(node = END_NODE, accDistance = 0))
                minDistFromEnd[END_NODE] = 0

                while (q.isNotEmpty()) {
                    val (node, accDistance) = q.poll()
                    if (accDistance > minDistFromEnd[node]) {
                        continue
                    }

                    for (edge in graph[node]) {
                        val nextDistance = accDistance + edge.distance
                        if (minDistFromEnd[edge.toNode] > nextDistance) {
                            minDistFromEnd[edge.toNode] = nextDistance
                q.offer(State(edge.toNode, nextDistance))
            }
        }
    }
}