import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.max
import java.util.*

data class Edge(val n1: Int, val n2: Int, val weight: Int)

lateinit var parent: IntArray

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val (nodeSize, edgeSize) = readLine().split(" ").map(String::toInt)
    val pq = PriorityQueue<Edge>(compareBy{it.weight})
    var edgeCount = 0
    var weightSum = 0
    parent = IntArray(nodeSize + 1){ it }

    repeat(edgeSize) {
        val st = StringTokenizer(readLine())
        val edge = Edge(st.nextToken().toInt(), st.nextToken().toInt(), st.nextToken().toInt())
        pq.add(edge)
    }

    while(edgeCount < nodeSize - 1) {
        val edge = pq.poll()

        if (find(edge.n1) == find(edge.n2)) continue

        union(edge.n1, edge.n2)
        weightSum += edge.weight
        edgeCount++
    }

    println(weightSum)
}

fun find(n: Int): Int {
    if (parent[n] == n) return n
    parent[n] = find(parent[n])
    return parent[n]
}

fun union(n1: Int, n2: Int) {
    val root1 = find(n1)
    val root2 = find(n2)
    if (root1 != root2) {
        parent[root2] = root1
    }
}