import java.util.PriorityQueue

class Solution {
    lateinit var parent: IntArray
    
    fun solution(n: Int, costs: Array<IntArray>): Int {
        var totalCost = 0
        val pq = PriorityQueue(compareBy<IntArray>{it[2]})
        var linkCount = 0
        parent = IntArray(n) { it }
        
        for (cost in costs) {
            pq.add(cost)
        }
        
        while (linkCount < n-1) {
            val edge = pq.poll()
            if (find(edge[0]) == find(edge[1])) continue
            
            union(edge[0], edge[1])
            linkCount++
            totalCost += edge[2]
        }
        
        return totalCost
    }
    
    fun find(island: Int): Int {
        if (parent[island] == island) return island
        parent[island] = find(parent[island])
        return parent[island]
    }
    
    fun union(i1: Int, i2: Int) {
        val root1 = find(i1)
        val root2 = find(i2)
        if (root1 != root2) {
            parent[root2] = root1
        }
    }
}