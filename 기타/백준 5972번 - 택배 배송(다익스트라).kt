/**
 * https://www.acmicpc.net/problem/5972
 * 다익스트라
 */
import java.util.StringTokenizer
import java.util.PriorityQueue

data class Edge(val dstNode: Int, val cost: Int)
data class Path(val currentNode: Int, val totalCost: Int)

fun main() {
    val (nodeCount, edgeCount) = readln().split(" ").map(String::toInt)
    val graph = Array(nodeCount + 1) { mutableListOf<Edge>() }
    repeat(edgeCount) {
        val st = StringTokenizer(readln())
        val node1 = st.nextToken().toInt()
        val node2 = st.nextToken().toInt()
        val cost = st.nextToken().toInt()
        graph[node1].add(Edge(dstNode = node2, cost = cost))
        graph[node2].add(Edge(dstNode = node1, cost = cost))
    }
    getMinCost(graph).let { print(it) }
}

private fun getMinCost(graph: Array<MutableList<Edge>>): Int {
    val pq = PriorityQueue(compareBy<Path> { it.totalCost })
    val costTable = IntArray(graph.size) { Int.MAX_VALUE }
    pq.offer(Path(1, 0))
    costTable[1] = 0

    while (pq.isNotEmpty()) {
        val (currentNode, totalCost) = pq.poll()
        if (totalCost > costTable[currentNode]) {
            continue
        }

        for (nextEdge in graph[currentNode]) {
            if (totalCost + nextEdge.cost < costTable[nextEdge.dstNode]) {
                costTable[nextEdge.dstNode] = totalCost + nextEdge.cost
                pq.offer(Path(nextEdge.dstNode, costTable[nextEdge.dstNode]))
            }
        }
    }

    return costTable[graph.size - 1]
}