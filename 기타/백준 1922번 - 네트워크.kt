/* https://www.acmicpc.net/problem/1922 */
/* 미니멈 스패닝 트리 (feat. union-find) */

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

data class Edge(val com1: Int, val com2: Int, val cost: Int)

lateinit var parent: IntArray

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val size = readLine().toInt()
    val edgeSize = readLine().toInt()
    parent = IntArray(size + 1) { it }
    val pq = PriorityQueue(compareBy<Edge>{it.cost})
    var linkCount = 0
    var totalCost = 0

    repeat(edgeSize) {
        with(StringTokenizer(readLine())){
            pq.add(Edge(nextToken().toInt(), nextToken().toInt(), nextToken().toInt()))
        }
    }

    // 최소 비용 -> 사이클 없이 모든 컴퓨터들이 연결되어야 함 -> size(edge) = size(node) - 1
    while(linkCount < size - 1) {
        val edge = pq.poll()

        if (find(edge.com1) != find(edge.com2)){
            union(edge.com1, edge.com2)
            linkCount++
            totalCost += edge.cost
        }
    }

    println(totalCost)
}

fun find(com: Int): Int {
    if (parent[com] == com) return com
    parent[com] = find(parent[com])
    return parent[com]
}

fun union(com1: Int, com2: Int) {
    val root1 = find(com1)
    val root2 = find(com2)
    if (root1 == root2) return
    parent[root2] = root1
}